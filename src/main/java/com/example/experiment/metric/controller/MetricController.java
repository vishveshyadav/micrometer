package com.example.experiment.metric.controller;

import com.example.experiment.metric.mapper.RequestToModelMapper;
import com.example.experiment.metric.model.TestModel;
import com.example.experiment.metric.model.TestModelRequest;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.validation.Valid;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;

@RestController
@CrossOrigin
@RequestMapping(path = MetricController.ENDPOINT)
public class MetricController {

    public static final String ENDPOINT = "/metric/test/";

    @Autowired
    private RequestToModelMapper requestToModelMapper;
    @Autowired
    private ExecutorService executorService;

    private Timer timer;
    private Counter counter;

    @PostMapping
    @Operation(summary = "API to create new Resource", description = "API to create new Resource")
    @Timed("getTestModel")
    public Mono<ResponseEntity<TestModel>> getTestModel(@Parameter(required = true) @Valid @RequestBody TestModelRequest testModelRequest) {
        return Mono.just(requestToModelMapper.requestToModel(testModelRequest))
                .name("fluxCreate")
                .subscribeOn(Schedulers.fromExecutorService(executorService))
                .map(testModel -> {
                    counter.increment();
                    return testModel;
                })
                .doOnNext(testModel -> System.out.println(testModel + "Processed on thread " + Thread.currentThread().getName()))
                .transform(processOutPut())
                .metrics();

    }

    private Function<Mono<TestModel>, Mono<ResponseEntity<TestModel>>> processOutPut() {
        return entity -> entity
                .doOnNext(testModel -> System.out.println("CREATED..." + testModel))
                .map(testModel -> ResponseEntity.status(HttpStatus.CREATED).body(testModel))
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }

    @Autowired
    public void initMetric(MeterRegistry meterRegistry) {
        this.timer = Timer
                .builder("apiTimer")
                .tag("uri", ENDPOINT)
                .publishPercentiles(0.95D, 0.99D, 0.98D)
                .publishPercentileHistogram()
                .register(meterRegistry);
        this.counter = Counter
                .builder("hitCounts")
                .tag("uri", ENDPOINT)
                .register(meterRegistry);
    }

}

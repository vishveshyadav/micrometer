package com.example.experiment.metric.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping(value = MonoController.ENDPOINT)
public class MonoController {

  public static final String ENDPOINT = "/metric/test";

  @Autowired
  private ExecutorService executorService;

  @GetMapping(path = "next/ten/numbers/{id}")
  @ApiOperation(value = "Number generator", notes = "Api to generate next 10 numbers from the passed number")
  public Mono<ResponseEntity<TreeSet<Integer>>> getNextNumber(
      @ApiParam(required = true, example = "10") @PathVariable Integer id) throws InterruptedException {
    Thread.sleep(5000);
    return Flux.range(++id, 10)
        .subscribeOn(Schedulers.fromExecutorService(executorService))
        .name("fluxCounter")
        .tag("uri", ENDPOINT)
        .doOnNext(integer -> System.out.println("Number Emitted " + integer + " on Thread " + Thread.currentThread().getName()))
        .collect(Collectors.toCollection(TreeSet::new))
        .transform(handleResponse())
        .metrics();

  }

  public Function<Mono<TreeSet<Integer>>, Mono<ResponseEntity<TreeSet<Integer>>>> handleResponse() {
    return integer -> integer
        .map(value -> {
          if (value.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body((TreeSet<Integer>) null);
          }
          return ResponseEntity.status(HttpStatus.ACCEPTED).body(value);
        })
        .defaultIfEmpty(ResponseEntity.status(HttpStatus.NO_CONTENT).body(null));
  }
}


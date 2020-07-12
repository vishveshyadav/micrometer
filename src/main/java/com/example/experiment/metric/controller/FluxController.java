package com.example.experiment.metric.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@RestController
@RequestMapping(value = FluxController.ENDPOINT)
public class FluxController {
    public static final String ENDPOINT = "/metric/test";

    @GetMapping(path = "next/ten/numbers/{id}")
    @ApiOperation(value = "Number generator", notes = "Api to generate next 10 numbers from the passed number")
    public Flux<ResponseEntity<Integer>> getNextNumber(
            @ApiParam(required = true, example = "10") @PathVariable Integer id) {

        return Flux.range(id, 11)
                .name("fluxCounter")
                .tag("uri",ENDPOINT)
                .doOnNext(integer -> System.out.println("Number Emitted " + integer))
                .transform(handleResponse())
                .metrics();

    }

    public Function<Flux<Integer>, Flux<ResponseEntity<Integer>>> handleResponse() {
        return integer -> integer
                .map(value -> ResponseEntity.status(HttpStatus.ACCEPTED).body(value))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NO_CONTENT).body((Integer) null));

    }
}


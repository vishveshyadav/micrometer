package com.example.experiment.metric.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping(value = MonoController.ENDPOINT)
public class MonoController {

  public static final String ENDPOINT = "/metric/test";

  @Autowired
  private ExecutorService executorService;

  @GetMapping(path = "next/ten/numbers/{id}")
  @Operation(summary = "Number generator", description = "Api to generate next 10 numbers from the passed number")
  public Mono<ResponseEntity<TreeSet<Integer>>> getNextNumber(
      @Parameter(required = true, example = "10") @PathVariable Integer id) {
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


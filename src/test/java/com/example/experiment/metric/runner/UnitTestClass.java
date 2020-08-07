package com.example.experiment.metric.runner;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.concurrent.*;

@ExtendWith(SpringExtension.class)
public class UnitTestClass {


  @Test
  public void test() {

    ThreadFactory threadFactory = new BasicThreadFactory.Builder().namingPattern("CustomThread -%d").build();
    ExecutorService threadpool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), threadFactory);

    Runnable runnable = () -> {
      System.out.println("Hello running inside runnable on thread :" + Thread.currentThread().getName());
    };

    Callable<Integer> callable = () -> {
      System.out.println("Hello running inside callable on thread :" + Thread.currentThread().getName());
      return 100;
    };

    Mono.fromFuture(CompletableFuture.runAsync(runnable, threadpool)
        .thenAccept(value -> System.out.println("Processed Inside runnable " + Thread.currentThread().getName())))
        .doOnError(error -> System.out.println("Error caught: " + error.getMessage()))
        .subscribe();
    Mono.fromCallable(callable)
        .subscribeOn(Schedulers.fromExecutorService(threadpool))
        .map(integer -> {
          System.out.println("Running on " + Thread.currentThread().getName());
          return integer;

        })
        .publishOn(Schedulers.fromExecutorService(threadpool))
        .doOnNext(integer -> System.out.println("Do On next on " + Thread.currentThread().getName()))
        .subscribe(integer -> System.out.println(integer + " Result Obtained on " + Thread.currentThread().getName()));
  }

  @Test
  public void testError() {
    Flux.range(1, 10)
        .flatMap(num -> {
          if (num > 5) {
            return Flux.error(new Error("Number > 5 " + "Number received is " + num));
          } else {
            return Flux.just(num);
          }
        })
        .doOnError(error -> System.out.println(error))
        .doOnError(error -> System.out.println("Emitting backup Flux"))
        .onErrorResume(error -> Flux.range(1, 5))
        .subscribe(num -> System.out.println(num));
  }

  @Test
  public void testErrorA() {
    Flux<Integer> flux = Flux.just(1, 2, 3, 4, 0, 21)
        .map(num -> 100 / num)
        .onErrorReturn(100);

    StepVerifier.create(flux)
        .expectNext(100, 50, 33, 25)
        .expectNext(100)
        .verifyComplete();
  }

  @Test
  public void testErrorB() {
    Flux<Integer> flux = Flux.just(1, 2, 3, 4, 0, 21)
        .map(num -> 100 / num)
        .onErrorContinue((error, value) -> {
          System.out.println("Error recved is " + error);
          System.out.println("value received is " + value);
        });
    // flux.subscribe(value -> System.out.println(value));

    StepVerifier.create(flux)
        .expectNext(100)
        .expectNext(50)
        .expectNext(33)
        .expectNext(25)
        .expectNextMatches(integer -> integer <= 100)
        .expectComplete()
        .verify();

    StepVerifier.create(flux)
        .expectNextCount(5)
        .expectComplete()
        .verify();
  }
}

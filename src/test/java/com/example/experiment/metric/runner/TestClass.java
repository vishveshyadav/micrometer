package com.example.experiment.metric.runner;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.*;

@ExtendWith(SpringExtension.class)
public class TestClass {

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
                    System.out.println("Running on "+Thread.currentThread().getName());
                    return integer;

                })
                .publishOn(Schedulers.fromExecutorService(threadpool))
                .doOnNext(integer -> System.out.println("Do On next on "+ Thread.currentThread().getName()))
                .subscribe(integer -> System.out.println(integer + " Result Obtained on "+ Thread.currentThread().getName()));

    }
}

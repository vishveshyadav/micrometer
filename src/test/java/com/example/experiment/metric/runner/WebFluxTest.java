package com.example.experiment.metric.runner;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class WebFluxTest {

    @Test
    public void testGenerate() {
        Flux<String> generate = Flux.generate(AtomicInteger::new, (state, sink) -> {
            sink.next("3 x " + state + " = " + (3 * state.getAndIncrement()));
            if (state.get() == 10) {
                sink.complete();
            }
            return state;
        }, state -> System.out.println("Final state: " + state));
        generate.subscribe(System.out::println);
    }

    @Test
    public void testCreate() {
        Flux<Integer> create = Flux.create(sink -> {
            for (int i = 1; i < 16; i++) {
                sink.next(i);
            }
            sink.complete();
        });
        create
                .doOnRequest(el -> System.out.println("Flux Requested " + el))
                .checkpoint("Create")
                .doOnEach(sigType -> System.out.println("Action occurred " + sigType))
                .doOnNext(System.out::println)
                .doFirst(() -> System.out.println("Going to subscribe"))
                .doFinally(sigType -> System.out.println("Processs Finished with " + sigType))
                .subscribe(System.out::println);
    }

    @Test
    public void testRun() {
        Thread thread = new Thread(() -> System.out.println("Hello from thread " + Thread.currentThread().getName()));
        thread.start();
    }

    @Test
    public void testCall() throws Exception {
        Future<String> stringFuture = acceptCallable(getString);
        System.out.println(stringFuture.get());
    }

    public Future<String> acceptCallable(Callable<String> task) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        return executorService.submit(task);
    }

    Callable<String> getString = () -> {
        System.out.println("Running in " + Thread.currentThread().getName());
        return "Hi from lambda";
    };

}

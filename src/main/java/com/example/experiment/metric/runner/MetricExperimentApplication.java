package com.example.experiment.metric.runner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.scheduler.Schedulers;

@SpringBootApplication(scanBasePackages = "com.example.experiment.metric")
public class MetricExperimentApplication {

  public static void main(String[] args) {
    Schedulers.enableMetrics();
    SpringApplication.run(MetricExperimentApplication.class, args);
  }

  @Bean
  public ExecutorService initThreadPool() {
    ThreadFactory threadPool = new BasicThreadFactory.Builder().namingPattern("CustomThread - %d").build();
    return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), threadPool);
  }
}

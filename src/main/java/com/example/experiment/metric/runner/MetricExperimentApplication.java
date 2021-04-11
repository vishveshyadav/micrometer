package com.example.experiment.metric.runner;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

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

  @EventListener(ApplicationReadyEvent.class)
  public void notification(){
    System.out.println("Application is now ready to serve requests");
    System.out.println("Access swagger at http://localhost:8085/swagger-ui.html");
  }

  @EventListener(AvailabilityChangeEvent.class)
  public void applicationStateChange(){
    System.out.println("Application state updated");
  }
}

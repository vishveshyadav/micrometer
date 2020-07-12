package com.example.experiment.metric.runner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.experiment.metric")
public class MetricExperimentApplication {
	public static void main(String[] args) {
		//Schedulers.enableMetrics();
		SpringApplication.run(MetricExperimentApplication.class, args);
	}
}

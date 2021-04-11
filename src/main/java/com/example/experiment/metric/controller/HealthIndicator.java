package com.example.experiment.metric.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.availability.ApplicationAvailability;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.LivenessState;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin
@RequestMapping(value = HealthIndicator.PATH)
public class HealthIndicator {

  public static final String PATH = "/health";

  @Autowired
  private ApplicationAvailability applicationAvailability;

  @Autowired
  private ApplicationEventPublisher eventPublisher;

  @GetMapping
  @Operation(summary = "Fetches Application current state", description = "Provides Liveness and Readiness probes value")
  public Mono<ResponseEntity<String>> getHealth() {
    LivenessState livenessState = applicationAvailability.getLivenessState();
    ReadinessState readinessState = applicationAvailability.getReadinessState();
    String response = "{\"Livenes\":\"" +livenessState.name()  +"\",\"Readiness\":\"" + readinessState.name()+"\"}";
    return Mono.just(ResponseEntity.status(HttpStatus.ACCEPTED).body(response));
  }

  @PutMapping(value = "/update/liveness")
  @Operation(summary = "Changes Liveness State to Broken", description = "Changes Liveness State to Broken")
  public Mono<Void> updateAppLivenessState() {
    LivenessState livenessState = applicationAvailability.getLivenessState();
    if (livenessState.equals(LivenessState.CORRECT)) {
      AvailabilityChangeEvent.publish(eventPublisher, "Changing State", LivenessState.BROKEN);
    }
    return Mono.empty();
  }

  @PutMapping(value = "/update/readiness")
  @Operation(summary = "Changes Readiness State to Broken", description = "Changes Readiness State to Broken")
  public Mono<Void> updateAppReadinessState() {
    ReadinessState readinessState = applicationAvailability.getReadinessState();
    if (readinessState.equals(ReadinessState.ACCEPTING_TRAFFIC)) {
      AvailabilityChangeEvent.publish(eventPublisher, "Changing State", ReadinessState.REFUSING_TRAFFIC);
    }
    return Mono.empty();
  }
}

package com.example.experiment.metric.runner;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class MetricConfigurations {

    @Bean
    public TimedAspect timedAspect(MeterRegistry meterRegistry){
        return new TimedAspect(meterRegistry);
    }

    @Bean
    public String hostName() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostName();
    }

    @Bean
    public MeterRegistryCustomizer customizer(String hostName) {
        return (registry) -> {
            registry.config().commonTags(new String[]{"host", hostName});
        };
    }

}

package com.example.experiment.metric.runner;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@ConfigurationProperties(prefix = "block.post")
public class CustomFilter implements WebFilter {

    @Getter
    @Setter
    private boolean enabled = false;

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        String methodValue = serverWebExchange.getRequest().getMethodValue();
        String path = serverWebExchange.getRequest().getPath().toString();
        if (enabled && methodValue.equals("PUT") && (path.equals("/health/update/liveness") || path.equals("/health/update/readiness"))) {
            log.info("Call BLocked !!! .. Method:{}, not allowed on resource:{}", methodValue, path);
            return Mono.empty();
        }
        return webFilterChain.filter(serverWebExchange);
    }
}

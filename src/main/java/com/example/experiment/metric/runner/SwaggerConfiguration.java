package com.example.experiment.metric.runner;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .host("")
                .protocols(protocols())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.experiment.metric"))
                .build()
                .apiInfo(metaInfo());
    }

    private ApiInfo metaInfo() {
        return new ApiInfoBuilder()
                .title("Local Test Metric Documentation")
                .description("Metric.")
                .version("1.0")
                .build();
    }


    private Set<String> protocols() {
        Set<String> protocols = new HashSet<>();
        protocols.add("http");
        return protocols;
    }


}

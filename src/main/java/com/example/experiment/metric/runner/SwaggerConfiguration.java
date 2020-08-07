package com.example.experiment.metric.runner;

import com.fasterxml.classmate.TypeResolver;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {

  private final TypeResolver resolver = new TypeResolver();

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .host("")
        .produces(Collections.singleton(MediaType.APPLICATION_JSON_VALUE))
        .protocols(protocols())
        .useDefaultResponseMessages(false)
        .ignoredParameterTypes(Mono.class, Flux.class)
        .genericModelSubstitutes(ResponseEntity.class)
        .alternateTypeRules(AlternateTypeRules.newRule(resolver.resolve(Mono.class,
            resolver.resolve(ResponseEntity.class, WildcardType.class)), WildcardType.class))
        .alternateTypeRules(AlternateTypeRules.newRule(resolver.resolve(Flux.class,
            resolver.resolve(ResponseEntity.class, WildcardType.class)), WildcardType.class))
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

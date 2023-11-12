package cmahy.springapp.reactive.controller;

import cmahy.springapp.reactive.controller.handler.StringGeneratorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration(proxyBeanMethods = false)
public class StringGeneratorController {

    @Bean
    public RouterFunction<ServerResponse> generatorRouter(StringGeneratorHandler handler) {
        return RouterFunctions
            .route(GET("/string-generator"), handler::generator);
    }
}

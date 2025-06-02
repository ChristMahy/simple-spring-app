package cmahy.simple.spring.webapp.reactive.resource.impl.adapter.config;

import cmahy.simple.spring.common.json.JsonMapperFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.*;
import org.springframework.web.reactive.config.*;

@Configuration
@EnableWebFlux
public class ApplicationConfigurer implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .maxAge(3600);
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return JsonMapperFactory.create();
    }

}

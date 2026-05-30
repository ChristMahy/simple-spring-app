package cmahy.simple.spring.brokers.consumer.core.config;

import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static tools.jackson.core.StreamWriteFeature.WRITE_BIGDECIMAL_AS_PLAIN;
import static tools.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static tools.jackson.databind.cfg.DateTimeFeature.WRITE_DATES_AS_TIMESTAMPS;

@Configuration
public class JsonMapperConfigurer {

    @Bean
    public JsonMapperBuilderCustomizer jsonCustomizer() {
        return builder -> builder
            .disable(WRITE_DATES_AS_TIMESTAMPS)
            .disable(FAIL_ON_UNKNOWN_PROPERTIES)
            .enable(WRITE_BIGDECIMAL_AS_PLAIN);
    }

}

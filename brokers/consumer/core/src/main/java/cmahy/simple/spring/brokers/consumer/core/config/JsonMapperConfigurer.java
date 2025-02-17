package cmahy.simple.spring.brokers.consumer.core.config;

import cmahy.simple.spring.common.json.JsonMapperFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class JsonMapperConfigurer {

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return JsonMapperFactory.create();
    }
}

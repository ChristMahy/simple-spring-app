package cmahy.simple.spring.webapp.resource.impl.adapter.config;

import cmahy.simple.spring.common.json.JsonMapperFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.*;

@Configuration
public class JsonMapperConfigurer {

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return JsonMapperFactory.create();
    }
}

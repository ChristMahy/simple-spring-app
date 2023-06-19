package cmahy.springapp.jms.publisher.config;

import cmahy.springapp.restresource.publisher.domain.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;

import java.util.Map;

@Configuration
public class JmsConfigurer {

    @Bean
    public MappingJackson2MessageConverter mappingJackson2MessageConverter(ObjectMapper objectMapper) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();

        converter.setObjectMapper(objectMapper);

        converter.setTypeIdPropertyName("_typeId");
        converter.setTypeIdMappings(Map.of(
            "message", Message.class
        ));

        return converter;
    }
}

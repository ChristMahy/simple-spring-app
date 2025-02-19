package cmahy.simple.spring.brokers.publisher.jms.config;

import cmahy.simple.spring.brokers.publisher.event.vo.output.MessageOutputEventVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;

import java.util.Map;

@Configuration
public class JmsPublisherConfigurer {

    @Bean
    public MappingJackson2MessageConverter mappingJackson2MessageConverter(ObjectMapper objectMapper) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();

        converter.setObjectMapper(objectMapper);

        converter.setTypeIdPropertyName("_typeId");
        converter.setTypeIdMappings(Map.of(
            "message", MessageOutputEventVo.class
        ));

        return converter;
    }
}

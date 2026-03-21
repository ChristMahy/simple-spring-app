package cmahy.simple.spring.brokers.consumer.jms.config;

import cmahy.simple.spring.brokers.consumer.message.event.vo.input.MessageInputEventVo;
import org.springframework.boot.artemis.autoconfigure.ArtemisAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.jms.support.converter.JacksonJsonMessageConverter;
import tools.jackson.databind.json.JsonMapper;

import java.util.Map;

@Configuration
@Import({ ArtemisAutoConfiguration.class })
public class JmsConfigurer {

    @Bean
    public JacksonJsonMessageConverter mappingJackson2MessageConverter(JsonMapper jsonMapper) {
        JacksonJsonMessageConverter converter = new JacksonJsonMessageConverter(jsonMapper);

        converter.setTypeIdPropertyName("_typeId");
        converter.setTypeIdMappings(Map.of(
            "message", MessageInputEventVo.class
        ));

        return converter;
    }
}

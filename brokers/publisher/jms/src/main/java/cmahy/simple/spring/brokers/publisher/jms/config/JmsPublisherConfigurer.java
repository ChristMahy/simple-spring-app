package cmahy.simple.spring.brokers.publisher.jms.config;

import cmahy.simple.spring.brokers.publisher.event.vo.output.MessageOutputEventVo;
import org.springframework.boot.artemis.autoconfigure.ArtemisAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.jms.support.converter.JacksonJsonMessageConverter;
import tools.jackson.databind.json.JsonMapper;

import java.util.Map;

@Configuration
@Import({ ArtemisAutoConfiguration.class })
public class JmsPublisherConfigurer {

    @Bean
    public JacksonJsonMessageConverter mappingJackson2MessageConverter(JsonMapper jsonMapper) {
        JacksonJsonMessageConverter converter = new JacksonJsonMessageConverter(jsonMapper);

        converter.setTypeIdPropertyName("_typeId");
        converter.setTypeIdMappings(Map.of(
            "message", MessageOutputEventVo.class
        ));

        return converter;
    }
}

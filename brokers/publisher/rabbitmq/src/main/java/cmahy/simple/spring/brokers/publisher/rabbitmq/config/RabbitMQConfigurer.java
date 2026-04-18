package cmahy.simple.spring.brokers.publisher.rabbitmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.amqp.autoconfigure.RabbitAutoConfiguration;
import org.springframework.context.annotation.*;
import tools.jackson.databind.json.JsonMapper;

@Configuration
@Import({RabbitAutoConfiguration.class})
public class RabbitMQConfigurer {

    @Bean
    public MessageConverter jacksonMessageConverter(JsonMapper jsonMapper) {
        return new JacksonJsonMessageConverter(jsonMapper);
    }

    @Bean
    public Queue messageModifyQueue() {
        return new Queue(RabbitMQQueue.MESSAGE_QUEUE_NAME + ".modify", true);
    }

    @Bean
    public Queue messageDeleteQueue() {
        return new Queue(RabbitMQQueue.MESSAGE_QUEUE_NAME + ".delete", true);
    }
}

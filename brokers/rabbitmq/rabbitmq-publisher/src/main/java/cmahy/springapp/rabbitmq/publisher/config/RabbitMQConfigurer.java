package cmahy.springapp.rabbitmq.publisher.config;

import cmahy.springapp.rabbitmq.publisher.RabbitMQQueue;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfigurer {

    @Bean
    public Jackson2JsonMessageConverter jacksonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public Queue tacoMessageQueue() {
        return new Queue(RabbitMQQueue.TACOCLOUD_MESSAGE, true);
    }
}

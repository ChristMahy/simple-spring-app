package cmahy.simple.spring.brokers.consumer.rabbitmq.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfigurer {

    @Bean
    public MessageConverter jacksonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public Queue messageModifyQueue() {
        return new Queue(RabbitMQQueue.MESSAGE_QUEUE_NAME + ".modify", true);
    }

    @Bean
    public Queue messageDeleteQueue() {
        return new Queue(RabbitMQQueue.MESSAGE_QUEUE_NAME + ".modify", true);
    }
}

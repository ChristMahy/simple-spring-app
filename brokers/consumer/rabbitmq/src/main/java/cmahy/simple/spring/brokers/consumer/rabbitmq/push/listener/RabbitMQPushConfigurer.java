package cmahy.simple.spring.brokers.consumer.rabbitmq.push.listener;

import cmahy.simple.spring.brokers.consumer.rabbitmq.config.RabbitMQConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({RabbitMQConfigurer.class})
public class RabbitMQPushConfigurer {
}

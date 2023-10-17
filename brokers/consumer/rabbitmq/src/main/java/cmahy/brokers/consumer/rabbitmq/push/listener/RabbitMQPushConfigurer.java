package cmahy.brokers.consumer.rabbitmq.push.listener;

import cmahy.brokers.consumer.rabbitmq.config.RabbitMQConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({RabbitMQConfigurer.class})
public class RabbitMQPushConfigurer {
}

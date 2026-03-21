package cmahy.simple.spring.brokers.consumer.core.adapter.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.*;

@Profile("rabbitmq")
@ConditionalOnProperty(name = "application.rabbitmq.consumer.type", havingValue = "push")
@Configuration
@ComponentScan("cmahy.simple.spring.brokers.consumer.rabbitmq.push")
public class RabbitMQPushProfileConfigurer {
}

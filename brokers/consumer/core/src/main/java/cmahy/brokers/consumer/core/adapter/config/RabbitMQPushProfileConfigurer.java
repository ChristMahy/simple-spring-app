package cmahy.brokers.consumer.core.adapter.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.*;

@Profile("rabbitmq")
@ConditionalOnProperty(name = "application.rabbitmq.consumer.type", havingValue = "push")
@Configuration
@ComponentScan("cmahy.brokers.consumer.rabbitmq.push")
public class RabbitMQPushProfileConfigurer {
}

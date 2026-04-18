package cmahy.simple.spring.brokers.publisher.core.adapter.config;

import org.springframework.context.annotation.*;

@Profile("rabbitmq")
@Configuration
@ComponentScan("cmahy.simple.spring.brokers.publisher.rabbitmq")
public class RabbitMQProfileConfigurer {
}

package cmahy.brokers.publisher.core.adapter.config;

import org.springframework.context.annotation.*;

@Profile("rabbitmq")
@Configuration
@ComponentScan("cmahy.brokers.publisher.rabbitmq")
public class RabbitMQProfileConfigurer {
}

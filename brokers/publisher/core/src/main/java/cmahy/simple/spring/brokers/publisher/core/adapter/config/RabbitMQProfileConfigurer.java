package cmahy.simple.spring.brokers.publisher.core.adapter.config;

import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.context.annotation.*;

@Profile("rabbitmq")
@Configuration
@Import({RabbitAutoConfiguration.class})
@ComponentScan("cmahy.simple.spring.brokers.publisher.rabbitmq")
public class RabbitMQProfileConfigurer {
}

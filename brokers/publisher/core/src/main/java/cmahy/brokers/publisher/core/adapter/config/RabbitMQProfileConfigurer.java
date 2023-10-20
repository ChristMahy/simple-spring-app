package cmahy.brokers.publisher.core.adapter.config;

import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.context.annotation.*;

@Profile("rabbitmq")
@Configuration
@Import({RabbitAutoConfiguration.class})
@ComponentScan("cmahy.brokers.publisher.rabbitmq")
public class RabbitMQProfileConfigurer {
}

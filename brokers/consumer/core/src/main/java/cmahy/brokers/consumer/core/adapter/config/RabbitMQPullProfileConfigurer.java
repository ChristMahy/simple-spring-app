package cmahy.brokers.consumer.core.adapter.config;

import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableScheduling;

@Profile("rabbitmq")
@ConditionalOnProperty(name = "application.rabbitmq.consumer.type", havingValue = "pull")
@Configuration
@Import({RabbitAutoConfiguration.class})
@ComponentScan("cmahy.brokers.consumer.rabbitmq.pull")
@EnableScheduling
public class RabbitMQPullProfileConfigurer {
}

package cmahy.simple.spring.brokers.consumer.core.adapter.config;

import org.springframework.context.annotation.*;

@Profile("kafka")
@Configuration
@ComponentScan("cmahy.simple.spring.brokers.consumer.kafka")
public class KafkaProfileConfigurer {
}

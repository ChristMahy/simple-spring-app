package cmahy.simple.spring.brokers.publisher.core.adapter.config;

import org.springframework.context.annotation.*;

@Profile("kafka")
@Configuration
@ComponentScan("cmahy.simple.spring.brokers.publisher.kafka")
public class KafkaProfileConfigurer {
}

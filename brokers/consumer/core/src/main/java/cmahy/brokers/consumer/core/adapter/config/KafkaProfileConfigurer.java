package cmahy.brokers.consumer.core.adapter.config;

import org.springframework.context.annotation.*;

@Profile("kafka")
@Configuration
@ComponentScan("cmahy.brokers.consumer.kafka")
public class KafkaProfileConfigurer {
}

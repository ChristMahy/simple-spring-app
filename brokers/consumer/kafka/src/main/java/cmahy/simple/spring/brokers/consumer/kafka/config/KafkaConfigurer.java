package cmahy.simple.spring.brokers.consumer.kafka.config;

import org.springframework.boot.kafka.autoconfigure.KafkaAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ KafkaAutoConfiguration.class })
public class KafkaConfigurer {
}

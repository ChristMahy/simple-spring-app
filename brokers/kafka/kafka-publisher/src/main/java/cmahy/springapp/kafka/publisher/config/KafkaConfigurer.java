package cmahy.springapp.kafka.publisher.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfigurer {

    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name("tacocloud")
            .partitions(1)
            .replicas(1)
            .compact()
            .build();
    }
}

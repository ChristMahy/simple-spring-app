package cmahy.simple.spring.brokers.publisher.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfigurer {

    @Bean
    public NewTopic topicMessageDefault() {
        return TopicBuilder.name(KafkaTopic.Message.DEFAULT)
            .partitions(1)
            .replicas(1)
            .compact()
            .build();
    }

    @Bean
    public NewTopic topicMessageModification() {
        return TopicBuilder.name(KafkaTopic.Message.MODIFY)
            .partitions(1)
            .replicas(1)
            .compact()
            .build();
    }

    @Bean
    public NewTopic topicMessageDelete() {
        return TopicBuilder.name(KafkaTopic.Message.DELETE)
            .partitions(1)
            .replicas(1)
            .compact()
            .build();
    }
}

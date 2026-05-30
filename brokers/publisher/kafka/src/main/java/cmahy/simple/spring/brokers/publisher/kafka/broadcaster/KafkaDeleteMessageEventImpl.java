package cmahy.simple.spring.brokers.publisher.kafka.broadcaster;

import cmahy.simple.spring.brokers.publisher.event.message.DeleteMessageEvent;
import cmahy.simple.spring.brokers.publisher.event.vo.id.MessageEventId;
import cmahy.simple.spring.brokers.publisher.kafka.config.KafkaTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

@Service
public class KafkaDeleteMessageEventImpl implements DeleteMessageEvent {

    private final KafkaTemplate<String, byte[]> kafkaTemplate;
    private final ObjectMapper jsonMapper;

    public KafkaDeleteMessageEventImpl(KafkaTemplate<String, byte[]> kafkaTemplate, ObjectMapper jsonMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.jsonMapper = jsonMapper;
    }

    @Override
    public void execute(MessageEventId id) {
        kafkaTemplate.send(
            KafkaTopic.Message.DELETE,
            UUID.randomUUID().toString(),
            jsonMapper.writeValueAsBytes(id)
        );
    }
}

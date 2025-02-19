package cmahy.simple.spring.brokers.publisher.kafka.broadcaster;

import cmahy.brokers.publisher.event.message.DeleteMessageEvent;
import cmahy.brokers.publisher.event.vo.id.MessageEventId;
import cmahy.simple.spring.brokers.publisher.kafka.config.KafkaTopic;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class KafkaDeleteMessageEventImpl implements DeleteMessageEvent {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaDeleteMessageEventImpl.class);

    private final KafkaTemplate<String, byte[]> kafkaTemplate;
    private final ObjectMapper jsonMapper;

    public KafkaDeleteMessageEventImpl(KafkaTemplate<String, byte[]> kafkaTemplate, ObjectMapper jsonMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.jsonMapper = jsonMapper;
    }

    @Override
    public void execute(MessageEventId id) {
        try {
            kafkaTemplate.send(
                KafkaTopic.Message.DELETE,
                UUID.randomUUID().toString(),
                jsonMapper.writeValueAsBytes(id)
            );
        } catch (JsonProcessingException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}

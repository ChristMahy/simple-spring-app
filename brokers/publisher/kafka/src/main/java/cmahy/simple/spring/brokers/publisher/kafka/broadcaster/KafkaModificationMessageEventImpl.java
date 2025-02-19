package cmahy.simple.spring.brokers.publisher.kafka.broadcaster;

import cmahy.simple.spring.brokers.publisher.event.message.ModificationMessageEvent;
import cmahy.simple.spring.brokers.publisher.event.vo.output.MessageOutputEventVo;
import cmahy.simple.spring.brokers.publisher.kafka.config.KafkaTopic;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class KafkaModificationMessageEventImpl implements ModificationMessageEvent {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaModificationMessageEventImpl.class);

    private final KafkaTemplate<String, byte[]> kafkaTemplate;
    private final ObjectMapper jsonMapper;

    public KafkaModificationMessageEventImpl(KafkaTemplate<String, byte[]> kafkaTemplate, ObjectMapper jsonMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.jsonMapper = jsonMapper;
    }

    @Override
    public void execute(MessageOutputEventVo output) {
        try {
            kafkaTemplate.send(
                KafkaTopic.Message.MODIFY,
                UUID.randomUUID().toString(),
                jsonMapper.writeValueAsBytes(output)
            );
        } catch (JsonProcessingException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}

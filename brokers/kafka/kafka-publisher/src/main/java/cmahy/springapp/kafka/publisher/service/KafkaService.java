package cmahy.springapp.kafka.publisher.service;

import cmahy.springapp.restresource.publisher.domain.Message;
import cmahy.springapp.restresource.publisher.service.SenderMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class KafkaService implements SenderMessage {

    private static final Logger LOG = getLogger(KafkaService.class);

    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    private final ObjectMapper objectMapper;

    public KafkaService(KafkaTemplate<String, byte[]> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void sendMessage(Message message) {
        try {
            kafkaTemplate.sendDefault(UUID.randomUUID().toString(), objectMapper.writeValueAsBytes(message));
        } catch (JsonProcessingException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}

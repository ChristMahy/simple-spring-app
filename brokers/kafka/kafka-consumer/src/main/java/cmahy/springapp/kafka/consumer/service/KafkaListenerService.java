package cmahy.springapp.kafka.consumer.service;

import cmahy.springapp.restresource.consumer.domain.Message;
import cmahy.springapp.restresource.consumer.repository.MessageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class KafkaListenerService {

    private static final Logger LOG = getLogger(KafkaListenerService.class);

    private final MessageRepository messageRepository;

    private final ObjectMapper objectMapper;

    public KafkaListenerService(MessageRepository messageRepository, ObjectMapper objectMapper) {
        this.messageRepository = messageRepository;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "tacocloud")
    public void receiveMessage(
        byte[] message,
        ConsumerRecord<String, byte[]> record
    ) {
        LOG.info(
            "Received from partition {} with timestamp {}",
            record.partition(),
            record.timestamp()
        );

        try {
            messageRepository.save(
                objectMapper.readValue(message, Message.class)
            );
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}

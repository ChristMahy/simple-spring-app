package cmahy.springapp.rabbitmq.consumer.service;

import cmahy.springapp.restresource.consumer.domain.Message;
import cmahy.springapp.restresource.consumer.repository.MessageRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class RabbitMQService {
    private static final Logger LOG = getLogger(RabbitMQService.class);

    private final MessageRepository messageRepository;

    public RabbitMQService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void receiveMessage(Message message) {
        LOG.info("A message has been received <{}>", message);

        messageRepository.save(message);
    }
}

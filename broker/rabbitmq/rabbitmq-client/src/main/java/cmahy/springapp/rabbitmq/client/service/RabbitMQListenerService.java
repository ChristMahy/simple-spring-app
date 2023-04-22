package cmahy.springapp.rabbitmq.client.service;

import cmahy.springapp.rabbitmq.client.domain.Message;
import cmahy.springapp.rabbitmq.client.repository.MessageRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class RabbitMQListenerService {
    private static final Logger LOG = getLogger(RabbitMQListenerService.class);

    private final MessageRepository messageRepository;

    public RabbitMQListenerService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

//    @JmsListener(destination = JmsQueue.TACOCLOUD_MESSAGE)
    public void receiveMessage(Message message) {
        LOG.info("A message has been received <{}>", message);

        messageRepository.save(message);
    }
}

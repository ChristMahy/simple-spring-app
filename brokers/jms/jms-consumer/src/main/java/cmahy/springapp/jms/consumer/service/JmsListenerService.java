package cmahy.springapp.jms.consumer.service;

import cmahy.springapp.restresource.consumer.domain.Message;
import cmahy.springapp.restresource.consumer.repository.MessageRepository;
import cmahy.springapp.jms.consumer.JmsQueue;
import org.slf4j.Logger;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class JmsListenerService {
    private static final Logger LOG = getLogger(JmsListenerService.class);

    private final MessageRepository messageRepository;

    public JmsListenerService(
            MessageRepository messageRepository
    ) {
        this.messageRepository = messageRepository;
    }

    @JmsListener(destination = JmsQueue.TACOCLOUD_MESSAGE)
    public void receiveMessage(Message message) {
        LOG.info("A message has been received <{}>", message);

        messageRepository.save(message);
    }
}

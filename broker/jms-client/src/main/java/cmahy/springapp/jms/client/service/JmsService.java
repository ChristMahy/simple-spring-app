package cmahy.springapp.jms.client.service;

import cmahy.springapp.jms.client.JmsQueue;
import cmahy.springapp.jms.client.domain.Message;
import cmahy.springapp.jms.client.repository.MessageRepository;
import org.slf4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class JmsService {
    private static final Logger LOG = getLogger(JmsService.class);

    private final JmsTemplate jms;
    private final MessageRepository messageRepository;

    public JmsService(
        JmsTemplate jms,
        MessageRepository messageRepository
    ) {
        this.jms = jms;
        this.messageRepository = messageRepository;
    }

    public void receiveMessage() {
        final Message message = (Message) jms.receiveAndConvert(JmsQueue.TACOCLOUD_MESSAGE);

        if (message != null) {
            LOG.info("A message has been received <{}>", message);

            messageRepository.save(message);
        }
    }
}

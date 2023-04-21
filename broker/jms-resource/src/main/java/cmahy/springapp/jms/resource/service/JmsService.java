package cmahy.springapp.jms.resource.service;

import cmahy.springapp.jms.resource.JmsQueue;
import cmahy.springapp.jms.resource.dto.Message;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class JmsService {
    private final JmsTemplate jms;

    public JmsService(
        JmsTemplate jms
    ) {
        this.jms = jms;
    }

    public void sendMessage(Message message) {
        jms.convertAndSend(
            JmsQueue.TACOCLOUD_MESSAGE, message
        );
    }
}

package cmahy.springapp.jms.publisher.service;

import cmahy.springapp.jms.publisher.JmsQueue;
import cmahy.springapp.jms.publisher.domain.Message;
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
            JmsQueue.TACOCLOUD_MESSAGE,
            message,
            (m) -> {
                m.setStringProperty("X_METADATA_HEADER", "More datas");

                return m;
            }
        );
    }
}

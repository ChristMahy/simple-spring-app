package cmahy.springapp.jms.publisher.service;

import cmahy.springapp.jms.publisher.JmsQueue;
import cmahy.springapp.restresource.publisher.domain.Message;
import cmahy.springapp.restresource.publisher.service.SenderMessage;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class JmsService implements SenderMessage {
    private final JmsTemplate jms;

    public JmsService(
        JmsTemplate jms
    ) {
        this.jms = jms;
    }

    @Override
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

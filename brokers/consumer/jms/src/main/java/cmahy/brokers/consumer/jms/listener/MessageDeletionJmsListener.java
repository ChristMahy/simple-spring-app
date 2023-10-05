package cmahy.brokers.consumer.jms.listener;

import cmahy.brokers.consumer.event.message.DeletionMessageListener;
import cmahy.brokers.consumer.event.vo.id.MessageEventId;
import cmahy.brokers.consumer.jms.config.JmsQueue;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class MessageDeletionJmsListener {

    private final DeletionMessageListener listener;

    public MessageDeletionJmsListener(DeletionMessageListener listener) {
        this.listener = listener;
    }

    @JmsListener(destination = JmsQueue.MESSAGE_QUEUE_NAME)
    public void receiveMessage(MessageEventId message) {
        listener.execute(message);
    }
}

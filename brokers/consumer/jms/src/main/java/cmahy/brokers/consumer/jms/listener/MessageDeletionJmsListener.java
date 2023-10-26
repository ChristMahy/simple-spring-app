package cmahy.brokers.consumer.jms.listener;

import cmahy.brokers.consumer.event.message.DeletionMessageListener;
import cmahy.brokers.consumer.event.vo.id.MessageEventId;
import cmahy.brokers.consumer.jms.config.JmsQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class MessageDeletionJmsListener {

    private static final Logger LOG = LoggerFactory.getLogger(MessageDeletionJmsListener.class);

    private final DeletionMessageListener listener;

    public MessageDeletionJmsListener(DeletionMessageListener listener) {
        this.listener = listener;
    }

    @JmsListener(destination = JmsQueue.MESSAGE_QUEUE_NAME + ".delete")
    public void receiveMessage(MessageEventId id) {
        try {
            LOG.info("Delete event received <{}>", id);

            listener.execute(id);
        } catch (Exception any) {
            LOG.error(any.getMessage(), any);
        }
    }
}

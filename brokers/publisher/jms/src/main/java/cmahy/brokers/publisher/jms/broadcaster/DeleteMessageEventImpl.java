package cmahy.brokers.publisher.jms.broadcaster;

import cmahy.brokers.publisher.event.message.DeleteMessageEvent;
import cmahy.brokers.publisher.event.vo.id.MessageEventId;
import cmahy.brokers.publisher.jms.config.JmsQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class DeleteMessageEventImpl implements DeleteMessageEvent {

    private static final Logger LOG = LoggerFactory.getLogger(DeleteMessageEventImpl.class);

    private final JmsTemplate jms;

    public DeleteMessageEventImpl(JmsTemplate jms) {
        this.jms = jms;
    }

    @Override
    public void execute(MessageEventId id) {
        LOG.debug("JMS, delete message sent <{}>", id.value());

        jms.convertAndSend(
            JmsQueue.MESSAGE_QUEUE_NAME + ".delete",
            id,
            (m) -> {
                m.setStringProperty("X_METADATA_HEADER", "More datas");

                return m;
            }
        );
    }
}

package cmahy.brokers.publisher.jms.broadcaster;

import cmahy.brokers.publisher.event.message.DeleteMessageEvent;
import cmahy.brokers.publisher.event.vo.id.MessageEventId;
import cmahy.brokers.publisher.jms.config.JmsQueue;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class DeleteMessageEventImpl implements DeleteMessageEvent {

    private final JmsTemplate jms;

    public DeleteMessageEventImpl(JmsTemplate jms) {
        this.jms = jms;
    }

    @Override
    public void execute(MessageEventId id) {
        jms.convertAndSend(
            JmsQueue.MESSAGE_QUEUE_NAME,
            id,
            (m) -> {
                m.setStringProperty("X_METADATA_HEADER", "More datas");

                return m;
            }
        );
    }
}

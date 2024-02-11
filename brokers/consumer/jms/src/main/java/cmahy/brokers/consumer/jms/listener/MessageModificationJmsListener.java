package cmahy.brokers.consumer.jms.listener;

import cmahy.brokers.consumer.message.event.ModificationMessageListener;
import cmahy.brokers.consumer.message.event.vo.input.MessageInputEventVo;
import cmahy.brokers.consumer.jms.config.JmsQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class MessageModificationJmsListener {

    private static final Logger LOG = LoggerFactory.getLogger(MessageModificationJmsListener.class);

    private final ModificationMessageListener listener;

    public MessageModificationJmsListener(ModificationMessageListener listener) {
        this.listener = listener;
    }

    @JmsListener(destination = JmsQueue.MESSAGE_QUEUE_NAME + ".modify")
    public void receiveMessage(MessageInputEventVo message) {
        try {
            LOG.info("Event received, message modification <{}>", message);

            listener.execute(message);
        } catch (Exception any) {
            LOG.error(any.getMessage(), any);
        }
    }
}

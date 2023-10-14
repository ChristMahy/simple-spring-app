package cmahy.brokers.publisher.jms.broadcaster;

import cmahy.brokers.publisher.event.message.ModificationMessageEvent;
import cmahy.brokers.publisher.event.vo.output.MessageOutputEventVo;
import cmahy.brokers.publisher.jms.config.JmsQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class JmsModificationMessageEventImpl implements ModificationMessageEvent {

    private static final Logger LOG = LoggerFactory.getLogger(JmsModificationMessageEventImpl.class);

    private final JmsTemplate jms;

    public JmsModificationMessageEventImpl(JmsTemplate jms) {
        this.jms = jms;
    }

    @Override
    public void execute(MessageOutputEventVo output) {
        LOG.debug("JMS, modify message sent <{}>", output);

        jms.convertAndSend(
            JmsQueue.MESSAGE_QUEUE_NAME + ".modify",
            output,
            (m) -> {
                m.setStringProperty("X_METADATA_HEADER", "More datas");

                return m;
            }
        );
    }
}

package cmahy.brokers.consumer.jms.listener;

import cmahy.brokers.consumer.api.vo.input.MessageInputApiVo;
import cmahy.brokers.consumer.event.message.ModificationMessageListener;
import cmahy.brokers.consumer.event.vo.input.MessageInputEventVo;
import cmahy.brokers.consumer.jms.config.JmsQueue;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageModificationJmsListener {

    private final ModificationMessageListener listener;

    public MessageModificationJmsListener(ModificationMessageListener listener) {
        this.listener = listener;
    }

    @JmsListener(destination = JmsQueue.MESSAGE_QUEUE_NAME + ".modify")
    public void receiveMessage(MessageInputEventVo message) {
        listener.execute(message);
    }
}

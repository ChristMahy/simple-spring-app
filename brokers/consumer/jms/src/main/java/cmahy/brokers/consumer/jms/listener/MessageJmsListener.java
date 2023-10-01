package cmahy.brokers.consumer.jms.listener;

import cmahy.brokers.consumer.api.message.ConsumeMessage;
import cmahy.brokers.consumer.api.vo.input.MessageInputApiVo;
import cmahy.brokers.consumer.jms.config.JmsQueue;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageJmsListener {

    private final ConsumeMessage consume;

    public MessageJmsListener(ConsumeMessage consume) {
        this.consume = consume;
    }

    @JmsListener(destination = JmsQueue.MESSAGE_QUEUE_NAME)
    public void receiveMessage(MessageInputApiVo message) {
        consume.execute(message, Optional.empty());
    }
}

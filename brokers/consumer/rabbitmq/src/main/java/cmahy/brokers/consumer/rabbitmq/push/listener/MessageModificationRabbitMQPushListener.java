package cmahy.brokers.consumer.rabbitmq.push.listener;

import cmahy.brokers.consumer.message.event.ModificationMessageListener;
import cmahy.brokers.consumer.message.event.vo.input.MessageInputEventVo;
import cmahy.brokers.consumer.rabbitmq.config.RabbitMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageModificationRabbitMQPushListener {

    private static final Logger LOG = LoggerFactory.getLogger(MessageModificationRabbitMQPushListener.class);

    private final ModificationMessageListener listener;

    public MessageModificationRabbitMQPushListener(ModificationMessageListener listener) {
        this.listener = listener;
    }

    @RabbitListener(queues = { RabbitMQQueue.MESSAGE_QUEUE_NAME + ".modify" })
    public void execute(MessageInputEventVo input) {
        try {
            listener.execute(input);
        } catch (Exception any) {
            LOG.error(any.getMessage(), any);
        }
    }
}

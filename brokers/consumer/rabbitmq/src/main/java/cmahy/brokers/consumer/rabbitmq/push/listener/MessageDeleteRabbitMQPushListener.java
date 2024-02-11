package cmahy.brokers.consumer.rabbitmq.push.listener;

import cmahy.brokers.consumer.message.event.DeletionMessageListener;
import cmahy.brokers.consumer.message.event.vo.id.MessageEventId;
import cmahy.brokers.consumer.rabbitmq.config.RabbitMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageDeleteRabbitMQPushListener {

    private static final Logger LOG = LoggerFactory.getLogger(MessageDeleteRabbitMQPushListener.class);

    private final DeletionMessageListener listener;

    public MessageDeleteRabbitMQPushListener(DeletionMessageListener listener) {
        this.listener = listener;
    }

    @RabbitListener(queues = { RabbitMQQueue.MESSAGE_QUEUE_NAME + ".delete" })
    public void execute(MessageEventId id) {
        try {
            listener.execute(id);
        } catch (Exception any) {
            LOG.error(any.getMessage(), any);
        }
    }
}

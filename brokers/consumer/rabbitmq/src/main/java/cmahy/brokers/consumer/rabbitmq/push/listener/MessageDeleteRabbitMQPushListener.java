package cmahy.brokers.consumer.rabbitmq.push.listener;

import cmahy.brokers.consumer.event.message.DeletionMessageListener;
import cmahy.brokers.consumer.event.vo.id.MessageEventId;
import cmahy.brokers.consumer.rabbitmq.config.RabbitMQQueue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageDeleteRabbitMQPushListener {

    private final DeletionMessageListener listener;

    public MessageDeleteRabbitMQPushListener(DeletionMessageListener listener) {
        this.listener = listener;
    }

    @RabbitListener(queues = { RabbitMQQueue.MESSAGE_QUEUE_NAME + ".delete" })
    public void execute(MessageEventId id) {
        listener.execute(id);
    }
}

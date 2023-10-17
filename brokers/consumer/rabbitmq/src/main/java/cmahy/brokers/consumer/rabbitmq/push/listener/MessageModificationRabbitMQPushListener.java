package cmahy.brokers.consumer.rabbitmq.push.listener;

import cmahy.brokers.consumer.event.message.ModificationMessageListener;
import cmahy.brokers.consumer.event.vo.input.MessageInputEventVo;
import cmahy.brokers.consumer.rabbitmq.config.RabbitMQQueue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageModificationRabbitMQPushListener {

    private final ModificationMessageListener listener;

    public MessageModificationRabbitMQPushListener(ModificationMessageListener listener) {
        this.listener = listener;
    }

    @RabbitListener(queues = { RabbitMQQueue.MESSAGE_QUEUE_NAME + ".modify" })
    public void execute(MessageInputEventVo input) {
        listener.execute(input);
    }
}

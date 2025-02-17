package cmahy.simple.spring.brokers.consumer.rabbitmq.pull.listener;

import cmahy.simple.spring.brokers.consumer.message.event.DeletionMessageListener;
import cmahy.simple.spring.brokers.consumer.message.event.vo.id.MessageEventId;
import cmahy.simple.spring.brokers.consumer.rabbitmq.config.RabbitMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class MessageDeleteRabbitMQPullListener {

    private static final Logger LOG = LoggerFactory.getLogger(MessageDeleteRabbitMQPullListener.class);

    private final RabbitTemplate rabbit;
    private final DeletionMessageListener listener;

    public MessageDeleteRabbitMQPullListener(
        RabbitTemplate rabbit,
        DeletionMessageListener listener
    ) {
        this.rabbit = rabbit;
        this.listener = listener;
    }

    @Scheduled(initialDelay = 2, fixedDelay = 5, timeUnit = TimeUnit.SECONDS)
    public void execute() {
        try {
            final MessageEventId message = rabbit.receiveAndConvert(
                RabbitMQQueue.MESSAGE_QUEUE_NAME + ".delete",
                new ParameterizedTypeReference<>() {
                }
            );

            if (message != null) {
                listener.execute(message);
            }
        } catch (Exception any) {
            LOG.error(any.getMessage(), any);
        }
    }
}

package cmahy.brokers.consumer.rabbitmq.pull.listener;

import cmahy.brokers.consumer.event.message.ModificationMessageListener;
import cmahy.brokers.consumer.event.vo.input.MessageInputEventVo;
import cmahy.brokers.consumer.rabbitmq.config.RabbitMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class MessageModificationRabbitMQPullListener {

    private static final Logger LOG = LoggerFactory.getLogger(MessageModificationRabbitMQPullListener.class);

    private final RabbitTemplate rabbit;
    private final ModificationMessageListener listener;


    public MessageModificationRabbitMQPullListener(
        RabbitTemplate rabbit,
        ModificationMessageListener listener
    ) {
        this.rabbit = rabbit;
        this.listener = listener;
    }

    @Scheduled(initialDelay = 2, fixedDelay = 5, timeUnit = TimeUnit.SECONDS)
    public void execute() {
        try {
            final MessageInputEventVo message = rabbit.receiveAndConvert(
                RabbitMQQueue.MESSAGE_QUEUE_NAME + ".modify",
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

package cmahy.brokers.publisher.rabbitmq.broadcaster;

import cmahy.brokers.publisher.rabbitmq.config.RabbitMQQueue;
import cmahy.brokers.publisher.event.message.DeleteMessageEvent;
import cmahy.brokers.publisher.event.vo.id.MessageEventId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQDeleteMessageEventImpl implements DeleteMessageEvent {

    private static final Logger LOG = LoggerFactory.getLogger(RabbitMQDeleteMessageEventImpl.class);

    private final RabbitTemplate rabbit;

    public RabbitMQDeleteMessageEventImpl(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
    }

    @Override
    public void execute(MessageEventId id) {
        LOG.debug("RabbitMQ, delete message sent <{}>", id.value());

        rabbit.convertAndSend(
            RabbitMQQueue.MESSAGE_QUEUE_NAME + ".delete",
            id,
            (m) -> {
                final var props = m.getMessageProperties();

                props.setHeader("X_METADATA_HEADER", "More datas");

                return m;
            }
        );
    }
}

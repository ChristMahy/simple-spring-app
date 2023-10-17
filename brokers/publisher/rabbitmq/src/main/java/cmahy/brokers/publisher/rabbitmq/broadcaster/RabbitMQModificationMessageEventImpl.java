package cmahy.brokers.publisher.rabbitmq.broadcaster;

import cmahy.brokers.publisher.rabbitmq.config.RabbitMQQueue;
import cmahy.brokers.publisher.event.message.ModificationMessageEvent;
import cmahy.brokers.publisher.event.vo.output.MessageOutputEventVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQModificationMessageEventImpl implements ModificationMessageEvent {

    private static final Logger LOG = LoggerFactory.getLogger(RabbitMQModificationMessageEventImpl.class);

    private final RabbitTemplate rabbit;

    public RabbitMQModificationMessageEventImpl(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
    }

    @Override
    public void execute(MessageOutputEventVo output) {
        LOG.debug("RabbitMQ, modify message sent <{}>", output);

        rabbit.convertAndSend(
            RabbitMQQueue.MESSAGE_QUEUE_NAME + ".modify",
            output,
            (m) -> {
                final var props = m.getMessageProperties();

                props.setHeader("X_METADATA_HEADER", "More datas");

                return m;
            }
        );
    }
}

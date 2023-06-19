package cmahy.springapp.rabbitmq.publisher.service;

import cmahy.springapp.rabbitmq.publisher.RabbitMQQueue;
import cmahy.springapp.restresource.publisher.domain.Message;
import cmahy.springapp.restresource.publisher.service.SenderMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService implements SenderMessage {

    private final RabbitTemplate rabbit;

    public RabbitMQService(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
    }

    public void sendMessage(Message message) {
        rabbit.convertAndSend(
            RabbitMQQueue.TACOCLOUD_MESSAGE,
            message,
            (m) -> {
                final var props = m.getMessageProperties();

                props.setHeader("X_METADATA_HEADER", "More datas");

                return m;
            }
        );
    }
}

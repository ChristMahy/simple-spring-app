package cmahy.springapp.rabbitmq.resource.service;

import cmahy.springapp.rabbitmq.resource.RabbitMQQueue;
import cmahy.springapp.rabbitmq.resource.domain.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService {

    private final RabbitTemplate rabbit;

    public RabbitMQService(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
    }

    public void sendMessage(Message message) {
        rabbit.convertAndSend(
            RabbitMQQueue.TACOCLOUD_MESSAGE,
            message
        );
    }
}

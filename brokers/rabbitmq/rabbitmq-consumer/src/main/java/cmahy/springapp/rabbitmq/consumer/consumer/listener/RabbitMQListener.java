package cmahy.springapp.rabbitmq.consumer.consumer.listener;

import cmahy.springapp.rabbitmq.consumer.RabbitMQQueue;
import cmahy.springapp.rabbitmq.consumer.service.RabbitMQService;
import cmahy.springapp.restresource.consumer.domain.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "application.rabbitmq.consumer.type", havingValue = "listener")
public class RabbitMQListener {

    private final RabbitMQService rabbitMQService;

    public RabbitMQListener(RabbitMQService rabbitMQService) {
        this.rabbitMQService = rabbitMQService;
    }

    @RabbitListener(queues = {RabbitMQQueue.TACOCLOUD_MESSAGE})
    public void listener(Message message) {
        rabbitMQService.receiveMessage(message);
    }
}

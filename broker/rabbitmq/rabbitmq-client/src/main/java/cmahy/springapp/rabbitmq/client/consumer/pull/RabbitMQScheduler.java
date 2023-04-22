package cmahy.springapp.rabbitmq.client.consumer.pull;

import cmahy.springapp.rabbitmq.client.RabbitMQQueue;
import cmahy.springapp.rabbitmq.client.domain.Message;
import cmahy.springapp.rabbitmq.client.service.RabbitMQService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.TimeUnit;

@Configuration
@ConditionalOnProperty(name = "application.rabbitmq.consumer.type", havingValue = "pull")
@EnableScheduling
public class RabbitMQScheduler {

    private final RabbitTemplate rabbit;
    private final RabbitMQService rabbitMQService;

    public RabbitMQScheduler(RabbitTemplate rabbit, RabbitMQService rabbitMQService) {
        this.rabbit = rabbit;
        this.rabbitMQService = rabbitMQService;
    }

    @Scheduled(initialDelay = 2, fixedDelay = 5, timeUnit = TimeUnit.SECONDS)
    public void schedulerOnQueue() {
        final Message message = rabbit.receiveAndConvert(
            RabbitMQQueue.TACOCLOUD_MESSAGE,
            //3000, // Timeout can be specified here
            new ParameterizedTypeReference<>() {}
        );

        if (message != null) {
            rabbitMQService.receiveMessage(message);
        }
    }
}

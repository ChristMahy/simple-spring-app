package cmahy.springapp.rabbitmq.resource.service;

import cmahy.springapp.rabbitmq.resource.domain.Message;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService {

    public void sendMessage(Message message) {
        throw new IllegalStateException("Not yet implemented");
    }
}

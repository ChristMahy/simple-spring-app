package cmahy.springapp.rabbitmq.client.repository;

import cmahy.springapp.rabbitmq.client.domain.Message;

import java.util.List;

public interface MessageRepository {
    List<Message> findAll();

    Message save(Message message);
}

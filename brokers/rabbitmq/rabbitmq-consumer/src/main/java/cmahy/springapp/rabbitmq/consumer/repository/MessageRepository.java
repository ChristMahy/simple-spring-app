package cmahy.springapp.rabbitmq.consumer.repository;

import cmahy.springapp.rabbitmq.consumer.domain.Message;

import java.util.List;

public interface MessageRepository {
    List<Message> findAll();

    Message save(Message message);
}

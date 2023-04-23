package cmahy.springapp.jms.consumer.repository;

import cmahy.springapp.jms.consumer.domain.Message;

import java.util.List;

public interface MessageRepository {
    List<Message> findAll();

    Message save(Message message);
}

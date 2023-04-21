package cmahy.springapp.jms.client.repository;

import cmahy.springapp.jms.client.domain.Message;

import java.util.List;

public interface MessageRepository {
    List<Message> findAll();

    Message save(Message message);
}

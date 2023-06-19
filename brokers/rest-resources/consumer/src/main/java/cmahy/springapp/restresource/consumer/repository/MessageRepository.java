package cmahy.springapp.restresource.consumer.repository;

import cmahy.springapp.restresource.consumer.domain.Message;

import java.util.List;

public interface MessageRepository {
    List<Message> findAll();

    Message save(Message message);
}

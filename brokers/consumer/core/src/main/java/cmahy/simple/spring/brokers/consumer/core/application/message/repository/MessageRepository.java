package cmahy.simple.spring.brokers.consumer.core.application.message.repository;

import cmahy.simple.spring.brokers.consumer.core.domain.message.Message;

import java.util.Optional;
import java.util.Set;

public interface MessageRepository {

    Set<Message> allMessages();

    Optional<Message> findById(Long id);

    Optional<Message> findByMessage(String content);

    Message save(Message message);

    void deleteById(Long id);
}

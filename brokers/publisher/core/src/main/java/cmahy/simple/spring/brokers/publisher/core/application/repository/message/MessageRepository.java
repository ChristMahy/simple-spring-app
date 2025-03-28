package cmahy.simple.spring.brokers.publisher.core.application.repository.message;

import cmahy.simple.spring.brokers.publisher.core.domain.Message;

import java.util.Optional;
import java.util.Set;

public interface MessageRepository {

    Set<Message> allMessages();

    Optional<Message> findById(Long id);

    Message save(Message message);

    void deleteById(Long id);
}

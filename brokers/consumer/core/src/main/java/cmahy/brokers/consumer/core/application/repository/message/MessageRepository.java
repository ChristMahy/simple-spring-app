package cmahy.brokers.consumer.core.application.repository.message;

import cmahy.brokers.consumer.core.domain.Message;

import java.util.Set;

public interface MessageRepository {

    Set<Message> allMessages();

    Message save(Message message);

    void delete(Message message);
}

package cmahy.brokers.publisher.core.application.repository.message;

import cmahy.brokers.publisher.core.domain.Message;

import java.util.Set;

public interface MessageRepository {

    Set<Message> allMessage();

    Message save(Message message);
}

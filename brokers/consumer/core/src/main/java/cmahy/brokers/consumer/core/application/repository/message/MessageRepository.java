package cmahy.brokers.consumer.core.application.repository.message;

import cmahy.brokers.consumer.core.application.vo.id.MessageAppId;
import cmahy.brokers.consumer.core.domain.Message;

import java.util.Optional;
import java.util.Set;

public interface MessageRepository {

    Set<Message> allMessages();

    Optional<Message> findById(Long id);

    Message save(Message message);

    void deleteById(Long id);
}

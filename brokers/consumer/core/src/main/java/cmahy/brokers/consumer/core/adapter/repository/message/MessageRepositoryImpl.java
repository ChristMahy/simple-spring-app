package cmahy.brokers.consumer.core.adapter.repository.message;

import cmahy.brokers.consumer.core.application.repository.message.MessageRepository;
import cmahy.brokers.consumer.core.domain.Message;
import jakarta.inject.Named;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Named
public class MessageRepositoryImpl implements MessageRepository {

    private final Set<Message> messages = new HashSet<>() {{
        add(new Message(1L, LocalDateTime.now(), "Message 1"));
        add(new Message(2L, LocalDateTime.now(), "Message 2"));
        add(new Message(3L, LocalDateTime.now(), "Message 3"));
        add(new Message(4L, LocalDateTime.now(), "Message 4"));
    }};

    @Override
    public Set<Message> allMessages() {
        return this.messages;
    }

    @Override
    public Message save(Message message) {
        throw new IllegalStateException("Not yet implemented !");
    }
}

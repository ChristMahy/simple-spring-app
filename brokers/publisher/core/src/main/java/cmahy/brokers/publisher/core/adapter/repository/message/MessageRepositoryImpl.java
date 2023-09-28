package cmahy.brokers.publisher.core.adapter.repository.message;

import cmahy.brokers.publisher.core.application.repository.message.MessageRepository;
import cmahy.brokers.publisher.core.domain.Message;
import jakarta.inject.Named;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Named
public class MessageRepositoryImpl implements MessageRepository {

    private final Set<Message> messages = new HashSet<>() {{
        add(new Message(1L, LocalDateTime.now(), "Message publisher 1"));
        add(new Message(2L, LocalDateTime.now(), "Message publisher 2"));
        add(new Message(3L, LocalDateTime.now(), "Message publisher 3"));
        add(new Message(4L, LocalDateTime.now(), "Message publisher 4"));
    }};

    @Override
    public Set<Message> allMessage() {
        return this.messages;
    }

    @Override
    public Message save(Message message) {
        throw new IllegalStateException("Not yet implemented !");
    }
}

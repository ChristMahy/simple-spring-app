package cmahy.brokers.consumer.core.adapter.repository.message;

import cmahy.brokers.consumer.core.application.repository.message.MessageRepository;
import cmahy.brokers.consumer.core.application.vo.id.MessageAppId;
import cmahy.brokers.consumer.core.domain.Message;
import cmahy.brokers.consumer.core.exception.ExpectedZeroMessageException;
import cmahy.brokers.consumer.core.exception.MoreThanOneFoundMessageException;
import cmahy.brokers.consumer.core.exception.message.IdShouldNotBeNullMessageException;
import jakarta.inject.Named;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    public Optional<Message> findById(Long id) {
        Set<Message> found = allMessages().stream()
            .filter(m -> Objects.equals(m.id(), id))
            .collect(Collectors.toSet());

        if (found.size() > 1) {
            throw new MoreThanOneFoundMessageException();
        }

        return found.stream().findFirst();
    }

    @Override
    public Message save(Message message) {
        long count = messages.stream().filter(m -> StringUtils.equalsIgnoreCase(m.message(), message.message())).count();

        if (count > 0) {
            throw new ExpectedZeroMessageException();
        }

        Message messageToSave = new Message(
            messages.stream().map(Message::id).max(Long::compare).orElse(1L),
            message.createdAt(),
            message.message()
        );

        messages.add(messageToSave);

        return messageToSave;
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            throw new IdShouldNotBeNullMessageException();
        }

        messages.stream()
            .filter(m -> Objects.equals(m.id(), id))
            .forEach(messages::remove);
    }
}

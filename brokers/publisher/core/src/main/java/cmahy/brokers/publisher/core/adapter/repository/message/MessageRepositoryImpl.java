package cmahy.brokers.publisher.core.adapter.repository.message;

import cmahy.brokers.publisher.core.application.repository.message.MessageRepository;
import cmahy.brokers.publisher.core.domain.Message;
import cmahy.brokers.publisher.core.exception.message.ExpectedZeroMessageException;
import cmahy.brokers.publisher.core.exception.message.IdShouldNotBeNullMessageException;
import cmahy.brokers.publisher.core.exception.message.MoreThanOneFoundMessageException;
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

    private final Set<Message> messages = new HashSet<>();

    @Override
    public Set<Message> allMessages() {
        return this.messages;
    }

    @Override
    public Message save(Message message) {
        long count = messages.stream().filter(m -> StringUtils.equalsIgnoreCase(m.message(), message.message())).count();

        if (count > 0) {
            throw new ExpectedZeroMessageException();
        }

        Message messageToSave = new Message(
            messages.stream().map(Message::id).max(Long::compare).orElse(1L) + 1,
            LocalDateTime.now(),
            message.message()
        );

        messages.add(messageToSave);

        return messageToSave;
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
    public void deleteById(Long id) {
        if (id == null) {
            throw new IdShouldNotBeNullMessageException();
        }

        messages.stream()
            .filter(m -> Objects.equals(m.id(), id))
            .forEach(messages::remove);
    }
}

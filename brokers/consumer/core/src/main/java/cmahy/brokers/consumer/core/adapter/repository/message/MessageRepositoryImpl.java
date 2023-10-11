package cmahy.brokers.consumer.core.adapter.repository.message;

import cmahy.brokers.consumer.core.application.repository.message.MessageRepository;
import cmahy.brokers.consumer.core.domain.Message;
import cmahy.brokers.consumer.core.exception.message.*;
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
    public Optional<Message> findById(Long id) {
        Set<Message> found = allMessages().stream()
            .filter(m -> Objects.equals(m.id(), id))
            .collect(Collectors.toSet());

        if (found.size() > 1) {
            throw new TooMuchElementFoundMessageException();
        }

        return found.stream().findFirst();
    }

    @Override
    public Message save(Message message) {
        Long id = message.id();

        if (id == null) {
            id = messages.stream().map(Message::id).max(Long::compare).orElse(0L) + 1;
        }

        long count = messages.stream()
            .filter(m ->
                StringUtils.equalsIgnoreCase(m.message(), message.message()) &&
                    !Objects.equals(m.id(), message.id())
            )
            .count();

        if (count > 0) {
            throw new DuplicateMessageException();
        }

        Message messageToSave = new Message(
            id,
            message.createdAt() != null ? message.createdAt() : LocalDateTime.now(),
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

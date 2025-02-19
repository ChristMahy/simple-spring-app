package cmahy.simple.spring.brokers.publisher.core.adapter.repository.message;

import cmahy.simple.spring.brokers.publisher.core.application.repository.message.MessageRepository;
import cmahy.simple.spring.brokers.publisher.core.domain.Message;
import cmahy.brokers.publisher.core.exception.message.*;
import cmahy.simple.spring.brokers.publisher.core.exception.message.DuplicateMessageException;
import cmahy.simple.spring.brokers.publisher.core.exception.message.TooMuchElementFoundMessageException;
import jakarta.inject.Named;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Named
public class MessageRepositoryImpl implements MessageRepository {

    private final ConcurrentHashMap<Long, Message> messages = new ConcurrentHashMap<>();

    @Override
    public Set<Message> allMessages() {
        return new HashSet<>(this.messages.values());
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
            id = messages.keySet().stream().max(Long::compare).orElse(0L) + 1;
        }

        long count = messages.values()
            .stream()
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

        messages.put(messageToSave.id(), messageToSave);

        return messageToSave;
    }

    @Override
    public void deleteById(Long id) {
        if (id != null) {
            messages.remove(id);
        }
    }
}

package cmahy.brokers.consumer.core.application.service.message;

import cmahy.brokers.consumer.core.application.repository.message.MessageRepository;
import cmahy.brokers.consumer.core.application.vo.id.MessageAppId;
import cmahy.brokers.consumer.core.domain.Message;
import cmahy.brokers.consumer.core.exception.MessageMoreThanOneFoundMessageException;
import jakarta.inject.Named;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Named
public class FindByIdMessageService {

    private final MessageRepository repository;

    public FindByIdMessageService(MessageRepository repository) {
        this.repository = repository;
    }

    public Optional<Message> execute(Optional<MessageAppId> id) {
        if (id.isEmpty()) {
            return Optional.empty();
        }

        Set<Message> found = repository.allMessages().stream()
            .filter(m -> Objects.equals(m.id(), id.get().value()))
            .collect(Collectors.toSet());

        if (found.size() > 1) {
            throw new MessageMoreThanOneFoundMessageException();
        }

        return found.stream().findFirst();
    }
}

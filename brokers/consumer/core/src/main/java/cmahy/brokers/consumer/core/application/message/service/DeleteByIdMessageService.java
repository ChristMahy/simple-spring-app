package cmahy.brokers.consumer.core.application.message.service;

import cmahy.brokers.consumer.core.application.message.repository.MessageRepository;
import cmahy.brokers.consumer.core.application.message.vo.id.MessageAppId;
import cmahy.brokers.consumer.core.exception.message.IdShouldNotBeNullMessageException;
import jakarta.inject.Named;

@Named
public class DeleteByIdMessageService {

    private final MessageRepository repository;

    public DeleteByIdMessageService(MessageRepository repository) {
        this.repository = repository;
    }

    public void execute(MessageAppId id) {
        if (id == null) {
            throw new IdShouldNotBeNullMessageException();
        }

        repository.deleteById(id.value());
    }
}

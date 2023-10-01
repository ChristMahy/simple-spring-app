package cmahy.brokers.consumer.core.application.service.message;

import cmahy.brokers.consumer.core.application.mapper.message.MessageAppMapper;
import cmahy.brokers.consumer.core.application.repository.message.MessageRepository;
import cmahy.brokers.consumer.core.application.vo.id.MessageAppId;
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

package cmahy.brokers.publisher.core.application.service.message;

import cmahy.brokers.publisher.core.application.repository.message.MessageRepository;
import cmahy.brokers.publisher.core.application.service.message.event.BroadcastMessageDeletion;
import cmahy.brokers.publisher.core.application.vo.id.MessageAppId;
import cmahy.brokers.publisher.core.exception.message.IdShouldNotBeNullMessageException;
import jakarta.inject.Named;

@Named
public class DeleteByIdMessageService {

    private final MessageRepository repository;
    private final BroadcastMessageDeletion broadcastDeletion;


    public DeleteByIdMessageService(
        MessageRepository repository,
        BroadcastMessageDeletion broadcastDeletion
    ) {
        this.repository = repository;
        this.broadcastDeletion = broadcastDeletion;
    }

    public void execute(MessageAppId id) {
        if (id == null) {
            throw new IdShouldNotBeNullMessageException();
        }

        repository.deleteById(id.value());

        broadcastDeletion.execute(id);
    }
}

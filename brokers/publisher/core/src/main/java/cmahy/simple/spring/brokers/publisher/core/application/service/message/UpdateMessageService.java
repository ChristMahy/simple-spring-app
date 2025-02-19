package cmahy.simple.spring.brokers.publisher.core.application.service.message;

import cmahy.simple.spring.brokers.publisher.core.application.mapper.message.MessageAppMapper;
import cmahy.simple.spring.brokers.publisher.core.application.repository.message.MessageRepository;
import cmahy.simple.spring.brokers.publisher.core.application.service.message.event.BroadcastMessageModification;
import cmahy.simple.spring.brokers.publisher.core.application.vo.id.MessageAppId;
import cmahy.simple.spring.brokers.publisher.core.application.vo.input.MessageInputAppVo;
import cmahy.simple.spring.brokers.publisher.core.application.vo.output.MessageOutputAppVo;
import cmahy.simple.spring.brokers.publisher.core.domain.Message;
import cmahy.simple.spring.brokers.publisher.core.exception.message.NotFoundMessageException;
import jakarta.inject.Named;

import java.time.LocalDateTime;

@Named
public class UpdateMessageService {

    private final MessageRepository repository;
    private final MessageAppMapper mapper;
    private final BroadcastMessageModification broadcastModification;

    public UpdateMessageService(
        MessageRepository repository,
        MessageAppMapper mapper,
        BroadcastMessageModification broadcastModification) {
        this.repository = repository;
        this.mapper = mapper;
        this.broadcastModification = broadcastModification;
    }

    public MessageOutputAppVo execute(MessageInputAppVo input, MessageAppId id) {
        Message byId = repository.findById(id.value())
            .orElseThrow(NotFoundMessageException::new);

        repository.deleteById(byId.id());

        Message toSave = new Message(
            byId.id(),
            LocalDateTime.now(),
            input.message()
        );

        Message saved = repository.save(toSave);

        MessageOutputAppVo output = mapper.entityToOutput(saved);

        broadcastModification.execute(output);

        return output;
    }
}

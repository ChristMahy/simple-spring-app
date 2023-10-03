package cmahy.brokers.publisher.core.application.service.message;

import cmahy.brokers.publisher.core.application.mapper.message.MessageAppMapper;
import cmahy.brokers.publisher.core.application.repository.message.MessageRepository;
import cmahy.brokers.publisher.core.application.vo.id.MessageAppId;
import cmahy.brokers.publisher.core.application.vo.input.MessageInputAppVo;
import cmahy.brokers.publisher.core.application.vo.output.MessageOutputAppVo;
import cmahy.brokers.publisher.core.domain.Message;
import cmahy.brokers.publisher.core.exception.message.NotFoundMessageException;
import jakarta.inject.Named;

import java.time.LocalDateTime;

@Named
public class UpdateMessageService {

    private final MessageRepository repository;
    private final MessageAppMapper mapper;

    public UpdateMessageService(
        MessageRepository repository,
        MessageAppMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
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

        return mapper.entityToOutput(saved);
    }
}

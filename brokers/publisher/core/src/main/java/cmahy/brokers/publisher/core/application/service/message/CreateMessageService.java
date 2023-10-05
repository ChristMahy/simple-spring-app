package cmahy.brokers.publisher.core.application.service.message;

import cmahy.brokers.publisher.core.application.mapper.message.MessageAppMapper;
import cmahy.brokers.publisher.core.application.repository.message.MessageRepository;
import cmahy.brokers.publisher.core.application.service.message.event.BroadcastMessageModification;
import cmahy.brokers.publisher.core.application.vo.input.MessageInputAppVo;
import cmahy.brokers.publisher.core.application.vo.output.MessageOutputAppVo;
import jakarta.inject.Named;

@Named
public class CreateMessageService {

    private final MessageRepository repository;
    private final MessageAppMapper mapper;
    private final BroadcastMessageModification broadcastModification;

    public CreateMessageService(
        MessageRepository repository,
        MessageAppMapper mapper,
        BroadcastMessageModification broadcastModification
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.broadcastModification = broadcastModification;
    }

    public MessageOutputAppVo execute(MessageInputAppVo input) {
        MessageOutputAppVo output = mapper.entityToOutput(
            repository.save(
                mapper.inputToEntity(input)
            )
        );

        broadcastModification.execute(output);

        return output;
    }
}

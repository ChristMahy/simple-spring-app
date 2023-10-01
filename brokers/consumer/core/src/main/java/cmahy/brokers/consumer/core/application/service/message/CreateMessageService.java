package cmahy.brokers.consumer.core.application.service.message;

import cmahy.brokers.consumer.core.application.mapper.message.MessageAppMapper;
import cmahy.brokers.consumer.core.application.repository.message.MessageRepository;
import cmahy.brokers.consumer.core.application.vo.input.MessageInputAppVo;
import cmahy.brokers.consumer.core.application.vo.output.MessageOutputAppVo;
import cmahy.brokers.consumer.core.domain.Message;
import jakarta.inject.Named;

@Named
public class CreateMessageService {

    private final MessageRepository repository;
    private final MessageAppMapper mapper;

    public CreateMessageService(
        MessageRepository repository,
        MessageAppMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public MessageOutputAppVo execute(MessageInputAppVo input) {
        return mapper.entityToOutput(
            repository.save(
                mapper.inputToEntity(input)
            )
        );
    }
}

package cmahy.brokers.publisher.core.application.service.message;

import cmahy.brokers.publisher.core.application.mapper.message.MessageAppMapper;
import cmahy.brokers.publisher.core.application.repository.message.MessageRepository;
import cmahy.brokers.publisher.core.application.vo.input.MessageInputAppVo;
import cmahy.brokers.publisher.core.application.vo.output.MessageOutputAppVo;
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

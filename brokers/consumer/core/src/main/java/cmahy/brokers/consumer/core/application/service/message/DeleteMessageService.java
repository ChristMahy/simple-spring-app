package cmahy.brokers.consumer.core.application.service.message;

import cmahy.brokers.consumer.core.application.mapper.message.MessageAppMapper;
import cmahy.brokers.consumer.core.application.repository.message.MessageRepository;
import cmahy.brokers.consumer.core.application.vo.input.MessageInputAppVo;
import jakarta.inject.Named;

@Named
public class DeleteMessageService {

    private final MessageRepository repository;
    private final MessageAppMapper mapper;

    public DeleteMessageService(MessageRepository repository, MessageAppMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public void execute(MessageInputAppVo message) {
        repository.delete(
            mapper.inputToEntity(message)
        );
    }
}

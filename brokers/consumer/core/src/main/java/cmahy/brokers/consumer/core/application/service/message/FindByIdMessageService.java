package cmahy.brokers.consumer.core.application.service.message;

import cmahy.brokers.consumer.core.application.mapper.message.MessageAppMapper;
import cmahy.brokers.consumer.core.application.repository.message.MessageRepository;
import cmahy.brokers.consumer.core.application.vo.id.MessageAppId;
import cmahy.brokers.consumer.core.application.vo.output.MessageOutputAppVo;
import jakarta.inject.Named;

import java.util.Optional;

@Named
public class FindByIdMessageService {

    private final MessageRepository repository;
    private final MessageAppMapper mapper;

    public FindByIdMessageService(
        MessageRepository repository,
        MessageAppMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Optional<MessageOutputAppVo> execute(Optional<MessageAppId> id) {
        if (id.isEmpty()) {
            return Optional.empty();
        }

        return repository.findById(id.get().value())
            .map(mapper::entityToOutput);
    }
}

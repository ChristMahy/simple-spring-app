package cmahy.brokers.consumer.core.application.service.message;

import cmahy.brokers.consumer.api.UriConstant;
import cmahy.brokers.consumer.core.application.mapper.message.MessageAppMapper;
import cmahy.brokers.consumer.core.application.repository.message.MessageRepository;
import cmahy.brokers.consumer.core.application.vo.input.MessageInputEventAppVo;
import cmahy.brokers.consumer.core.application.vo.output.MessageOutputAppVo;
import cmahy.brokers.consumer.core.domain.Message;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Named
public class ReceiveAMessageFromEventService {

    private static final Logger LOG = LoggerFactory.getLogger(ReceiveAMessageFromEventService.class);
    
    private final MessageRepository repository;
    private final MessageAppMapper mapper;

    public ReceiveAMessageFromEventService(
        MessageRepository repository,
        MessageAppMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public MessageOutputAppVo execute(MessageInputEventAppVo input) {
        LOG.info("Message received <{}>", input);

        Message message = repository
            .findByMessage(input.message())
            .map(m -> {
                LOG.info("Update message");

                repository.deleteById(m.id());

                return new Message(
                    m.id(),
                    input.createdAt(),
                    input.message(),
                    m.modificationCounter() != null ? m.modificationCounter() + 1 : 2
                );
            })
            .orElse(mapper.inputToEntity(input));

        return mapper.entityToOutput(
            repository.save(message)
        );
    }
}

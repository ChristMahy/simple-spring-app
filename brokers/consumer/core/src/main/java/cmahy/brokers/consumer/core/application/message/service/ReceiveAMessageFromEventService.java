package cmahy.brokers.consumer.core.application.message.service;

import cmahy.brokers.consumer.core.application.message.mapper.MessageAppMapper;
import cmahy.brokers.consumer.core.application.message.repository.MessageRepository;
import cmahy.brokers.consumer.core.application.message.vo.input.MessageInputEventAppVo;
import cmahy.brokers.consumer.core.application.message.vo.output.MessageOutputAppVo;
import cmahy.brokers.consumer.core.domain.message.Message;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            .findById(input.id().value())
            .map(m -> {
                LOG.info("Update message");

                repository.deleteById(m.id());

                // TODO: Maybe mapper to process an update, message to message
                return new Message(
                    m.id(),
                    input.createdAt(),
                    input.message(),
                    m.modificationCounter() != null ? m.modificationCounter() + 1 : 2
                );
            })
            .orElseGet(() -> mapper.inputToEntity(input));

        return mapper.entityToOutput(
            repository.save(message)
        );
    }
}

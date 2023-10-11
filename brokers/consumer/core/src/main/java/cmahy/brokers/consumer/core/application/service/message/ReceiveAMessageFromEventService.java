package cmahy.brokers.consumer.core.application.service.message;

import cmahy.brokers.consumer.core.application.mapper.message.MessageAppMapper;
import cmahy.brokers.consumer.core.application.repository.message.MessageRepository;
import cmahy.brokers.consumer.core.application.vo.input.MessageInputEventAppVo;
import cmahy.brokers.consumer.core.application.vo.output.MessageOutputAppVo;
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
        return mapper.entityToOutput(
            repository.save(
                mapper.inputToEntity(input)
            )
        );
    }
}

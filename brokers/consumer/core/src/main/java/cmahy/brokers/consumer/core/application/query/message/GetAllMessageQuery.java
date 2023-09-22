package cmahy.brokers.consumer.core.application.query.message;

import cmahy.brokers.consumer.core.application.mapper.message.MessageAppMapper;
import cmahy.brokers.consumer.core.application.repository.message.MessageRepository;
import cmahy.brokers.consumer.core.application.vo.output.MessageOutputAppVo;
import cmahy.common.annotation.Query;
import jakarta.inject.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Query
@Named
public class GetAllMessageQuery {

    private final MessageRepository messageRepository;
    private final MessageAppMapper messageAppMapper;

    public GetAllMessageQuery(
        MessageRepository messageRepository,
        MessageAppMapper messageAppMapper
    ) {
        this.messageRepository = messageRepository;
        this.messageAppMapper = messageAppMapper;
    }

    public Set<MessageOutputAppVo> execute() {
        return this.messageRepository.allMessages().stream()
            .map(messageAppMapper::entityToOutput)
            .collect(Collectors.toSet());
    }
}

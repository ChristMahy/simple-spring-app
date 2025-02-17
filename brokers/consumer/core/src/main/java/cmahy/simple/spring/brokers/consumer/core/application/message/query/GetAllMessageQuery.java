package cmahy.simple.spring.brokers.consumer.core.application.message.query;

import cmahy.simple.spring.brokers.consumer.core.application.message.mapper.MessageAppMapper;
import cmahy.simple.spring.brokers.consumer.core.application.message.repository.MessageRepository;
import cmahy.simple.spring.brokers.consumer.core.application.message.vo.output.MessageOutputAppVo;
import cmahy.simple.spring.common.annotation.Query;
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

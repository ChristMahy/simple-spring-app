package cmahy.brokers.publisher.core.application.query.message;

import cmahy.brokers.publisher.core.application.mapper.message.MessageAppMapper;
import cmahy.brokers.publisher.core.application.repository.message.MessageRepository;
import cmahy.brokers.publisher.core.application.vo.output.MessageOutputAppVo;
import cmahy.common.annotation.Query;
import jakarta.inject.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Query
@Named
public class GetAllMessageQuery {

    private final MessageRepository repository;
    private final MessageAppMapper mapper;

    public GetAllMessageQuery(
        MessageRepository repository,
        MessageAppMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Set<MessageOutputAppVo> execute() {
        return repository.allMessages().stream()
            .map(mapper::entityToOutput)
            .collect(Collectors.toSet());
    }
}

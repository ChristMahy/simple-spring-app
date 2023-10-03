package cmahy.brokers.publisher.core.adapter.api.message;

import cmahy.brokers.publisher.api.message.MessageApi;
import cmahy.brokers.publisher.api.vo.id.MessageApiId;
import cmahy.brokers.publisher.api.vo.input.MessageInputApiVo;
import cmahy.brokers.publisher.api.vo.output.MessageOutputApiVo;
import cmahy.brokers.publisher.core.adapter.mapper.message.MessageAdapterMapper;
import cmahy.brokers.publisher.core.application.query.message.GetAllMessageQuery;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class MessageApiImpl implements MessageApi {

    private final GetAllMessageQuery query;
    private final MessageAdapterMapper mapper;

    public MessageApiImpl(GetAllMessageQuery query, MessageAdapterMapper mapper) {
        this.query = query;
        this.mapper = mapper;
    }

    @Override
    public Iterable<MessageOutputApiVo> allMessages() {
        return query.execute().stream()
            .map(mapper::toApiVo)
            .collect(Collectors.toSet());
    }

    @Override
    public MessageOutputApiVo create(MessageInputApiVo input) {
        throw new IllegalStateException("Not yet implemented !");
    }

    @Override
    public MessageOutputApiVo update(MessageInputApiVo input, MessageApiId id) {
        throw new IllegalStateException("Not yet implemented !");
    }

    @Override
    public void delete(MessageApiId id) {
        throw new IllegalStateException("Not yet implemented !");
    }
}

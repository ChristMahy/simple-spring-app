package cmahy.brokers.publisher.core.adapter.api.message;

import cmahy.brokers.publisher.api.message.MessageApi;
import cmahy.brokers.publisher.api.vo.id.MessageApiId;
import cmahy.brokers.publisher.api.vo.input.MessageInputApiVo;
import cmahy.brokers.publisher.api.vo.output.MessageOutputApiVo;
import cmahy.brokers.publisher.core.adapter.mapper.message.MessageAdapterMapper;
import cmahy.brokers.publisher.core.adapter.mapper.message.MessageInputAdapterMapper;
import cmahy.brokers.publisher.core.application.command.message.CreateMessageCommand;
import cmahy.brokers.publisher.core.application.query.message.GetAllMessageQuery;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class MessageApiImpl implements MessageApi {

    private final GetAllMessageQuery query;
    private final MessageAdapterMapper mapper;
    private final MessageInputAdapterMapper inputMapper;
    private final CreateMessageCommand createCommand;

    public MessageApiImpl(
        GetAllMessageQuery query,
        MessageAdapterMapper mapper,
        MessageInputAdapterMapper inputMapper,
        CreateMessageCommand createCommand
    ) {
        this.query = query;
        this.mapper = mapper;
        this.inputMapper = inputMapper;
        this.createCommand = createCommand;
    }

    @Override
    public Iterable<MessageOutputApiVo> allMessages() {
        return query.execute().stream()
            .map(mapper::toApiVo)
            .collect(Collectors.toSet());
    }

    @Override
    public MessageOutputApiVo create(MessageInputApiVo input) {
        return mapper.toApiVo(
            createCommand.execute(
                inputMapper.toAppVo(input)
            )
        );
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

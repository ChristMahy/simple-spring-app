package cmahy.brokers.publisher.core.adapter.api.message;

import cmahy.brokers.publisher.api.message.MessageApi;
import cmahy.brokers.publisher.api.vo.id.MessageApiId;
import cmahy.brokers.publisher.api.vo.input.MessageInputApiVo;
import cmahy.brokers.publisher.api.vo.output.MessageOutputApiVo;
import cmahy.brokers.publisher.core.adapter.mapper.message.MessageAdapterMapper;
import cmahy.brokers.publisher.core.adapter.mapper.message.MessageInputAdapterMapper;
import cmahy.brokers.publisher.core.application.command.message.CreateMessageCommand;
import cmahy.brokers.publisher.core.application.command.message.DeleteMessageCommand;
import cmahy.brokers.publisher.core.application.command.message.UpdateMessageCommand;
import cmahy.brokers.publisher.core.application.query.message.GetAllMessageQuery;
import cmahy.brokers.publisher.core.application.vo.id.MessageAppId;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class MessageApiImpl implements MessageApi {

    private final GetAllMessageQuery query;
    private final MessageAdapterMapper mapper;
    private final MessageInputAdapterMapper inputMapper;
    private final CreateMessageCommand createCommand;
    private final UpdateMessageCommand updateCommand;
    private final DeleteMessageCommand deleteCommand;

    public MessageApiImpl(
        GetAllMessageQuery query,
        MessageAdapterMapper mapper,
        MessageInputAdapterMapper inputMapper,
        CreateMessageCommand createCommand,
        UpdateMessageCommand updateCommand,
        DeleteMessageCommand deleteCommand
    ) {
        this.query = query;
        this.mapper = mapper;
        this.inputMapper = inputMapper;
        this.createCommand = createCommand;
        this.updateCommand = updateCommand;
        this.deleteCommand = deleteCommand;
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
    public MessageOutputApiVo update(MessageInputApiVo input, Long id) {
        return mapper.toApiVo(
            updateCommand.execute(
                inputMapper.toAppVo(input), new MessageAppId(id)
            )
        );
    }

    @Override
    public void delete(MessageApiId id) {
        deleteCommand.execute(new MessageAppId(id.value()));
    }
}

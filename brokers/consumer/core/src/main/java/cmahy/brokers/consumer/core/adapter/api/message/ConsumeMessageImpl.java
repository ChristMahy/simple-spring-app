package cmahy.brokers.consumer.core.adapter.api.message;

import cmahy.brokers.consumer.api.message.ConsumeMessage;
import cmahy.brokers.consumer.api.vo.id.MessageApiId;
import cmahy.brokers.consumer.api.vo.input.MessageInputApiVo;
import cmahy.brokers.consumer.core.adapter.mapper.message.MessageInputAdapterMapper;
import cmahy.brokers.consumer.core.application.command.message.SaveMessageCommand;
import cmahy.brokers.consumer.core.application.vo.id.MessageAppId;
import jakarta.inject.Named;

import java.util.Optional;

@Named
public class ConsumeMessageImpl implements ConsumeMessage {

    private final SaveMessageCommand command;
    private final MessageInputAdapterMapper mapper;

    public ConsumeMessageImpl(SaveMessageCommand command, MessageInputAdapterMapper mapper) {
        this.command = command;
        this.mapper = mapper;
    }

    @Override
    public void execute(MessageInputApiVo input, Optional<MessageApiId> id) {
        command.execute(mapper.toAppVo(input), id.map(i -> new MessageAppId(i.value())));
    }
}

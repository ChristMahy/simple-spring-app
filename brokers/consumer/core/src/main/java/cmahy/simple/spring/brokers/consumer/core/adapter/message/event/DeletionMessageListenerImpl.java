package cmahy.simple.spring.brokers.consumer.core.adapter.message.event;

import cmahy.simple.spring.brokers.consumer.core.adapter.message.mapper.MessageIdAdapterMapper;
import cmahy.simple.spring.brokers.consumer.core.application.message.command.DeleteMessageCommand;
import cmahy.simple.spring.brokers.consumer.message.event.DeletionMessageListener;
import cmahy.simple.spring.brokers.consumer.message.event.vo.id.MessageEventId;
import jakarta.inject.Named;

@Named
public class DeletionMessageListenerImpl implements DeletionMessageListener {

    private final DeleteMessageCommand command;

    private final MessageIdAdapterMapper idMapper;

    public DeletionMessageListenerImpl(
        DeleteMessageCommand command,
        MessageIdAdapterMapper idMapper
    ) {
        this.command = command;
        this.idMapper = idMapper;
    }

    @Override
    public void execute(MessageEventId id) {
        command.execute(idMapper.toAppId(id));
    }
}

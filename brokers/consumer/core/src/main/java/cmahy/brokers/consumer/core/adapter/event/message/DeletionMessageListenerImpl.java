package cmahy.brokers.consumer.core.adapter.event.message;

import cmahy.brokers.consumer.core.adapter.mapper.message.MessageIdAdapterMapper;
import cmahy.brokers.consumer.core.application.command.message.DeleteMessageCommand;
import cmahy.brokers.consumer.event.message.DeletionMessageListener;
import cmahy.brokers.consumer.event.vo.id.MessageEventId;
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

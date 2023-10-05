package cmahy.brokers.consumer.core.adapter.event.message;

import cmahy.brokers.consumer.core.application.command.message.DeleteMessageCommand;
import cmahy.brokers.consumer.core.application.vo.id.MessageAppId;
import cmahy.brokers.consumer.event.message.DeletionMessageListener;
import cmahy.brokers.consumer.event.vo.id.MessageEventId;
import jakarta.inject.Named;

@Named
public class DeletionMessageListenerImpl implements DeletionMessageListener {

    private final DeleteMessageCommand command;

    public DeletionMessageListenerImpl(DeleteMessageCommand command) {
        this.command = command;
    }

    @Override
    public void execute(MessageEventId id) {
        command.execute(new MessageAppId(id.value()));
    }
}

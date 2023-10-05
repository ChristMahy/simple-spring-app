package cmahy.brokers.publisher.core.adapter.service.message;

import cmahy.brokers.publisher.core.application.service.message.event.BroadcastMessageDeletion;
import cmahy.brokers.publisher.core.application.vo.id.MessageAppId;
import cmahy.brokers.publisher.event.message.DeleteMessageEvent;
import cmahy.brokers.publisher.event.vo.id.MessageEventId;
import jakarta.inject.Named;

@Named
public class BroadcastMessageDeletionImpl implements BroadcastMessageDeletion {

    private final DeleteMessageEvent event;

    public BroadcastMessageDeletionImpl(DeleteMessageEvent event) {
        this.event = event;
    }

    @Override
    public void execute(MessageAppId id) {
        event.execute(new MessageEventId(id.value()));
    }
}

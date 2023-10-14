package cmahy.brokers.publisher.core.adapter.service.message;

import cmahy.brokers.publisher.core.application.service.message.event.BroadcastMessageDeletion;
import cmahy.brokers.publisher.core.application.vo.id.MessageAppId;
import cmahy.brokers.publisher.event.message.DeleteMessageEvent;
import cmahy.brokers.publisher.event.vo.id.MessageEventId;
import jakarta.inject.Named;

import java.util.List;

@Named
public class BroadcastMessageDeletionImpl implements BroadcastMessageDeletion {

    private final List<DeleteMessageEvent> broadcasters;

    public BroadcastMessageDeletionImpl(
        List<DeleteMessageEvent> broadcasters
    ) {
        this.broadcasters = broadcasters;
    }

    @Override
    public void execute(MessageAppId id) {
        broadcasters.forEach(e -> e.execute(new MessageEventId(id.value())));
    }
}

package cmahy.brokers.publisher.event.message;

import cmahy.brokers.publisher.event.vo.id.MessageEventId;

public interface DeleteMessageEvent {

    void execute(MessageEventId id);
}

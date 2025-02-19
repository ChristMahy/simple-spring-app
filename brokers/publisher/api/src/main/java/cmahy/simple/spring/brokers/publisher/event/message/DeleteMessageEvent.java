package cmahy.simple.spring.brokers.publisher.event.message;

import cmahy.simple.spring.brokers.publisher.event.vo.id.MessageEventId;

public interface DeleteMessageEvent {

    void execute(MessageEventId id);
}

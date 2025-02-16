package cmahy.simple.spring.brokers.consumer.message.event;

import cmahy.simple.spring.brokers.consumer.message.event.vo.id.MessageEventId;

public interface DeletionMessageListener {

    void execute(MessageEventId id);
}

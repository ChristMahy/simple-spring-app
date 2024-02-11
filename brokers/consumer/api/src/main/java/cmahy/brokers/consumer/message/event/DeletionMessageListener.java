package cmahy.brokers.consumer.message.event;

import cmahy.brokers.consumer.message.event.vo.id.MessageEventId;

public interface DeletionMessageListener {

    void execute(MessageEventId id);
}

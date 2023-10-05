package cmahy.brokers.consumer.event.message;

import cmahy.brokers.consumer.event.vo.id.MessageEventId;

public interface DeletionMessageListener {

    void execute(MessageEventId id);
}

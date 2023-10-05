package cmahy.brokers.publisher.core.application.service.message.event;

import cmahy.brokers.publisher.core.application.vo.id.MessageAppId;

public interface BroadcastMessageDeletion {

    void execute(MessageAppId id);
}

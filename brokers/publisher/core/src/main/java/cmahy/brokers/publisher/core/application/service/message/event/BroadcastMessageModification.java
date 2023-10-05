package cmahy.brokers.publisher.core.application.service.message.event;

import cmahy.brokers.publisher.core.application.vo.output.MessageOutputAppVo;

public interface BroadcastMessageModification {

    void execute(MessageOutputAppVo output);
}

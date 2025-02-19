package cmahy.simple.spring.brokers.publisher.core.application.service.message.event;

import cmahy.simple.spring.brokers.publisher.core.application.vo.output.MessageOutputAppVo;

public interface BroadcastMessageModification {

    void execute(MessageOutputAppVo output);
}

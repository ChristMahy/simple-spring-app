package cmahy.simple.spring.brokers.publisher.event.message;

import cmahy.simple.spring.brokers.publisher.event.vo.output.MessageOutputEventVo;

public interface ModificationMessageEvent {

    void execute(MessageOutputEventVo output);
}

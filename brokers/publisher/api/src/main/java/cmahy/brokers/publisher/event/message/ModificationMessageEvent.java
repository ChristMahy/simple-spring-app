package cmahy.brokers.publisher.event.message;

import cmahy.brokers.publisher.event.vo.output.MessageOutputEventVo;

public interface ModificationMessageEvent {

    void execute(MessageOutputEventVo output);
}

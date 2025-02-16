package cmahy.simple.spring.brokers.consumer.message.event;

import cmahy.simple.spring.brokers.consumer.message.event.vo.input.MessageInputEventVo;

public interface ModificationMessageListener {

    void execute(MessageInputEventVo input);
}

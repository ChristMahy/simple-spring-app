package cmahy.brokers.consumer.message.event;

import cmahy.brokers.consumer.message.event.vo.input.MessageInputEventVo;

public interface ModificationMessageListener {

    void execute(MessageInputEventVo input);
}

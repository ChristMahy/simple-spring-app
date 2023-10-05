package cmahy.brokers.consumer.event.message;

import cmahy.brokers.consumer.event.vo.input.MessageInputEventVo;

public interface ModificationMessageListener {

    void execute(MessageInputEventVo input);
}

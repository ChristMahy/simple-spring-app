package cmahy.brokers.consumer.api.message;

import cmahy.brokers.consumer.api.vo.id.MessageApiId;
import cmahy.brokers.consumer.api.vo.input.MessageInputApiVo;

import java.util.Optional;

public interface ConsumeMessage {

    void execute(MessageInputApiVo input, Optional<MessageApiId> id);
}

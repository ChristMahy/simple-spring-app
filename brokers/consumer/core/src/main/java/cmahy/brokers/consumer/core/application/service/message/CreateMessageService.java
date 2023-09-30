package cmahy.brokers.consumer.core.application.service.message;

import cmahy.brokers.consumer.core.application.vo.input.MessageInputAppVo;
import cmahy.brokers.consumer.core.domain.Message;
import jakarta.inject.Named;

@Named
public class CreateMessageService {
    public Message execute(MessageInputAppVo input) {
        throw new IllegalStateException("Not yet implemented !");
    }
}

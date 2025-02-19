package cmahy.simple.spring.brokers.publisher.core.application.command.message;

import cmahy.simple.spring.brokers.publisher.core.application.service.message.CreateMessageService;
import cmahy.simple.spring.brokers.publisher.core.application.vo.input.MessageInputAppVo;
import cmahy.simple.spring.brokers.publisher.core.application.vo.output.MessageOutputAppVo;
import cmahy.simple.spring.common.annotation.Command;
import jakarta.inject.Named;

@Named
@Command
public class CreateMessageCommand {

    private final CreateMessageService create;

    public CreateMessageCommand(CreateMessageService create) {
        this.create = create;
    }

    public MessageOutputAppVo execute(MessageInputAppVo input) {
        return create.execute(input);
    }
}

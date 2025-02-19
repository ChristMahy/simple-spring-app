package cmahy.simple.spring.brokers.publisher.core.application.command.message;

import cmahy.simple.spring.brokers.publisher.core.application.service.message.UpdateMessageService;
import cmahy.simple.spring.brokers.publisher.core.application.vo.id.MessageAppId;
import cmahy.simple.spring.brokers.publisher.core.application.vo.input.MessageInputAppVo;
import cmahy.simple.spring.brokers.publisher.core.application.vo.output.MessageOutputAppVo;
import cmahy.simple.spring.common.annotation.Command;
import jakarta.inject.Named;

@Named
@Command
public class UpdateMessageCommand {

    private final UpdateMessageService update;

    public UpdateMessageCommand(UpdateMessageService update) {
        this.update = update;
    }

    public MessageOutputAppVo execute(MessageInputAppVo input, MessageAppId id) {
        return update.execute(input, id);
    }
}

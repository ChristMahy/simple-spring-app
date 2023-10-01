package cmahy.brokers.consumer.core.application.command.message;

import cmahy.brokers.consumer.core.application.mapper.message.MessageAppMapper;
import cmahy.brokers.consumer.core.application.service.message.SaveMessageService;
import cmahy.brokers.consumer.core.application.vo.id.MessageAppId;
import cmahy.brokers.consumer.core.application.vo.input.MessageInputAppVo;
import cmahy.brokers.consumer.core.application.vo.output.MessageOutputAppVo;
import cmahy.common.annotation.Command;
import jakarta.inject.Named;

import java.util.Optional;

@Command
@Named
public class SaveMessageCommand {

    private final SaveMessageService save;

    public SaveMessageCommand(SaveMessageService save) {
        this.save = save;
    }

    public MessageOutputAppVo execute(MessageInputAppVo input, Optional<MessageAppId> id) {
        return save.execute(input, id);
    }
}

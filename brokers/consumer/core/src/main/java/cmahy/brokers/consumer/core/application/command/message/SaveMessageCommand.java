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
    private final MessageAppMapper mapper;

    public SaveMessageCommand(SaveMessageService save, MessageAppMapper mapper) {
        this.save = save;
        this.mapper = mapper;
    }

    public MessageOutputAppVo execute(MessageInputAppVo input, Optional<MessageAppId> id) {
        return mapper.entityToOutput(
            save.execute(input, id)
        );
    }
}

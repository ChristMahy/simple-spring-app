package cmahy.brokers.consumer.core.application.command.message;

import cmahy.brokers.consumer.core.application.service.message.ReceiveAMessageFromEventService;
import cmahy.brokers.consumer.core.application.vo.input.MessageInputEventAppVo;
import cmahy.brokers.consumer.core.application.vo.output.MessageOutputAppVo;
import cmahy.common.annotation.Command;
import jakarta.inject.Named;

@Command
@Named
public class ReceiveAMessageFromEventCommand {

    private final ReceiveAMessageFromEventService save;

    public ReceiveAMessageFromEventCommand(ReceiveAMessageFromEventService save) {
        this.save = save;
    }

    public MessageOutputAppVo execute(MessageInputEventAppVo input) {
        return save.execute(input);
    }
}

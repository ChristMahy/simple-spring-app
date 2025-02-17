package cmahy.simple.spring.brokers.consumer.core.application.message.command;

import cmahy.simple.spring.brokers.consumer.core.application.message.service.ReceiveAMessageFromEventService;
import cmahy.simple.spring.brokers.consumer.core.application.message.vo.input.MessageInputEventAppVo;
import cmahy.simple.spring.brokers.consumer.core.application.message.vo.output.MessageOutputAppVo;
import cmahy.simple.spring.common.annotation.Command;
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

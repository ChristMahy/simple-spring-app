package cmahy.brokers.consumer.core.application.command.message;

import cmahy.brokers.consumer.core.application.service.message.DeleteByIdMessageService;
import cmahy.brokers.consumer.core.application.vo.id.MessageAppId;
import cmahy.common.annotation.Command;
import jakarta.inject.Named;

@Command
@Named
public class DeleteMessageCommand {

    private final DeleteByIdMessageService delete;

    public DeleteMessageCommand(DeleteByIdMessageService delete) {
        this.delete = delete;
    }

    public void execute(MessageAppId id) {
        delete.execute(id);
    }
}

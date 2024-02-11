package cmahy.brokers.consumer.core.application.message.command;

import cmahy.brokers.consumer.core.application.message.service.DeleteByIdMessageService;
import cmahy.brokers.consumer.core.application.message.vo.id.MessageAppId;
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

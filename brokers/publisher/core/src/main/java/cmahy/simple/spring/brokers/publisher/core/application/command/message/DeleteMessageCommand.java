package cmahy.simple.spring.brokers.publisher.core.application.command.message;

import cmahy.simple.spring.brokers.publisher.core.application.service.message.DeleteByIdMessageService;
import cmahy.simple.spring.brokers.publisher.core.application.vo.id.MessageAppId;
import jakarta.inject.Named;

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

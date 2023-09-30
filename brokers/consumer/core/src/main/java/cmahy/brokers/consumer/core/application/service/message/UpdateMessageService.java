package cmahy.brokers.consumer.core.application.service.message;

import cmahy.brokers.consumer.core.application.vo.input.MessageInputAppVo;
import cmahy.brokers.consumer.core.domain.Message;
import jakarta.inject.Named;

@Named
public class UpdateMessageService {

    private final CreateMessageService create;
    private final DeleteMessageService delete;

    public UpdateMessageService(CreateMessageService create, DeleteMessageService delete) {
        this.create = create;
        this.delete = delete;
    }

    public Message execute(MessageInputAppVo input) {
        delete.execute(input);

        return create.execute(input);
    }
}

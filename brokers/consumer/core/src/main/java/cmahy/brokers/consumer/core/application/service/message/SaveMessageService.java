package cmahy.brokers.consumer.core.application.service.message;

import cmahy.brokers.consumer.core.application.vo.id.MessageAppId;
import cmahy.brokers.consumer.core.application.vo.input.MessageInputAppVo;
import cmahy.brokers.consumer.core.domain.Message;
import jakarta.inject.Named;

import java.util.Optional;

@Named
public class SaveMessageService {
    
    private final FindByIdMessageService findById;
    private final CreateMessageService create;
    private final UpdateMessageService update;

    public SaveMessageService(
        FindByIdMessageService findById,
        CreateMessageService create,
        UpdateMessageService update
    ) {
        this.findById = findById;
        this.create = create;
        this.update = update;
    }

    public Message execute(MessageInputAppVo input, Optional<MessageAppId> id) {
        Optional<Message> message = findById.execute(id);

        if (message.isPresent()) {
            return update.execute(input);
        } else {
            return create.execute(input);
        }
    }
}

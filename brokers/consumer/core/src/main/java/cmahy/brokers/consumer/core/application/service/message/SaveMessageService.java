package cmahy.brokers.consumer.core.application.service.message;

import cmahy.brokers.consumer.core.application.vo.id.MessageAppId;
import cmahy.brokers.consumer.core.application.vo.input.MessageInputAppVo;
import cmahy.brokers.consumer.core.application.vo.output.MessageOutputAppVo;
import cmahy.brokers.consumer.core.domain.Message;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Named
public class SaveMessageService {

    private static final Logger LOG = LoggerFactory.getLogger(SaveMessageService.class);
    
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

    public MessageOutputAppVo execute(MessageInputAppVo input, Optional<MessageAppId> id) {
        Optional<MessageOutputAppVo> message = findById.execute(id);

        if (message.isPresent()) {
            LOG.debug("Update message");

            return update.execute(input, id.get());
        } else {
            LOG.debug("Create message");

            return create.execute(input);
        }
    }
}

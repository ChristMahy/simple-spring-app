package cmahy.brokers.consumer.core.adapter.event.message;

import cmahy.brokers.consumer.core.adapter.mapper.message.MessageInputAdapterMapper;
import cmahy.brokers.consumer.core.application.command.message.SaveMessageCommand;
import cmahy.brokers.consumer.core.application.vo.id.MessageAppId;
import cmahy.brokers.consumer.event.message.ModificationMessageListener;
import cmahy.brokers.consumer.event.vo.input.MessageInputEventVo;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Named
public class ModificationMessageListenerImpl implements ModificationMessageListener {

    private static final Logger LOG = LoggerFactory.getLogger(ModificationMessageListenerImpl.class);

    private final SaveMessageCommand command;
    private final MessageInputAdapterMapper mapper;

    public ModificationMessageListenerImpl(SaveMessageCommand command, MessageInputAdapterMapper mapper) {
        this.command = command;
        this.mapper = mapper;
    }

    @Override
    public void execute(MessageInputEventVo input) {
        LOG.debug("Message received: <{}>", input);

        command.execute(
            mapper.toAppVo(input),
            Optional.of(new MessageAppId(input.id().value()))
        );
    }
}

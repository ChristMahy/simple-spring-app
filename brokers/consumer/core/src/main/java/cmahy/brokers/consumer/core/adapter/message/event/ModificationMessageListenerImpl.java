package cmahy.brokers.consumer.core.adapter.message.event;

import cmahy.brokers.consumer.core.adapter.message.mapper.MessageInputAdapterMapper;
import cmahy.brokers.consumer.core.application.message.command.ReceiveAMessageFromEventCommand;
import cmahy.brokers.consumer.message.event.ModificationMessageListener;
import cmahy.brokers.consumer.message.event.vo.input.MessageInputEventVo;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
public class ModificationMessageListenerImpl implements ModificationMessageListener {

    private static final Logger LOG = LoggerFactory.getLogger(ModificationMessageListenerImpl.class);

    private final ReceiveAMessageFromEventCommand command;
    private final MessageInputAdapterMapper mapper;

    public ModificationMessageListenerImpl(ReceiveAMessageFromEventCommand command, MessageInputAdapterMapper mapper) {
        this.command = command;
        this.mapper = mapper;
    }

    @Override
    public void execute(MessageInputEventVo input) {
        LOG.debug("Message received: <{}>", input);

        command.execute(
            mapper.toAppVo(input)
        );
    }
}

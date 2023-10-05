package cmahy.brokers.publisher.core.adapter.service.message;

import cmahy.brokers.publisher.core.adapter.mapper.message.MessageAdapterMapper;
import cmahy.brokers.publisher.core.application.service.message.event.BroadcastMessageModification;
import cmahy.brokers.publisher.core.application.vo.output.MessageOutputAppVo;
import cmahy.brokers.publisher.event.message.ModificationMessageEvent;
import jakarta.inject.Named;

@Named
public class BroadcastMessageModificationImpl implements BroadcastMessageModification {

    private final ModificationMessageEvent event;
    private final MessageAdapterMapper mapper;

    public BroadcastMessageModificationImpl(
        ModificationMessageEvent event,
        MessageAdapterMapper mapper
    ) {
        this.event = event;
        this.mapper = mapper;
    }

    @Override
    public void execute(MessageOutputAppVo output) {
        event.execute(mapper.toEventVo(output));
    }
}

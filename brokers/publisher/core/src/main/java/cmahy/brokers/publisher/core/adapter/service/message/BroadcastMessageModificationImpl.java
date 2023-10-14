package cmahy.brokers.publisher.core.adapter.service.message;

import cmahy.brokers.publisher.core.adapter.mapper.message.MessageAdapterMapper;
import cmahy.brokers.publisher.core.application.service.message.event.BroadcastMessageModification;
import cmahy.brokers.publisher.core.application.vo.output.MessageOutputAppVo;
import cmahy.brokers.publisher.event.message.ModificationMessageEvent;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Named
public class BroadcastMessageModificationImpl implements BroadcastMessageModification {

    private static final Logger LOG = LoggerFactory.getLogger(BroadcastMessageModificationImpl.class);

    private final List<ModificationMessageEvent> broadcasters;
    private final MessageAdapterMapper mapper;

    public BroadcastMessageModificationImpl(
        List<ModificationMessageEvent> broadcasters,
        MessageAdapterMapper mapper
    ) {
        this.broadcasters = broadcasters;
        this.mapper = mapper;
    }

    @Override
    public void execute(MessageOutputAppVo output) {
        LOG.debug("Message broadcasted <{}>", output.message());

        broadcasters.forEach(e -> e.execute(mapper.toEventVo(output)));
    }
}

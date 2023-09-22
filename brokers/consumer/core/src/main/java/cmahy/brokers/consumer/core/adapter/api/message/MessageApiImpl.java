package cmahy.brokers.consumer.core.adapter.api.message;

import cmahy.brokers.consumer.api.message.MessageApi;
import cmahy.brokers.consumer.core.adapter.mapper.message.MessageAdapterMapper;
import cmahy.brokers.consumer.core.application.query.message.GetAllMessageQuery;
import cmahy.brokers.consumer.vo.output.MessageOutputApiVo;
import jakarta.inject.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Named
public class MessageApiImpl implements MessageApi {

    private final GetAllMessageQuery getAllMessageQuery;
    private final MessageAdapterMapper messageAdapterMapper;

    public MessageApiImpl(
        GetAllMessageQuery getAllMessageQuery,
        MessageAdapterMapper messageAdapterMapper
    ) {
        this.getAllMessageQuery = getAllMessageQuery;
        this.messageAdapterMapper = messageAdapterMapper;
    }

    @Override
    public Set<MessageOutputApiVo> allMessages() {
        return this.getAllMessageQuery.execute().stream()
            .map(messageAdapterMapper::toApiVo)
            .collect(Collectors.toSet());
    }
}

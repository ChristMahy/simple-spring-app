package cmahy.simple.spring.brokers.consumer.core.adapter.message.mapper;

import cmahy.simple.spring.brokers.consumer.core.application.message.vo.output.MessageOutputAppVo;
import cmahy.simple.spring.brokers.consumer.message.api.vo.output.MessageOutputApiVo;
import jakarta.inject.Named;

@Named
public class MessageOutputAdapterMapper {

    private final MessageIdAdapterMapper idMapper;

    public MessageOutputAdapterMapper(MessageIdAdapterMapper idMapper) {
        this.idMapper = idMapper;
    }

    public MessageOutputApiVo toApiVo(MessageOutputAppVo output) {
        if (output == null) {
            return null;
        }

        return new MessageOutputApiVo(
            idMapper.toApiId(output.id()),
            output.createdAt(),
            output.message(),
            output.modificationCounter()
        );
    }
}

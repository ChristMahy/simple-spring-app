package cmahy.brokers.consumer.core.adapter.mapper.message;

import cmahy.brokers.consumer.core.application.vo.output.MessageOutputAppVo;
import cmahy.brokers.consumer.api.vo.output.MessageOutputApiVo;
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

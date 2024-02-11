package cmahy.brokers.consumer.core.application.message.mapper;

import cmahy.brokers.consumer.core.application.message.vo.input.MessageInputEventAppVo;
import cmahy.brokers.consumer.core.application.message.vo.output.MessageOutputAppVo;
import cmahy.brokers.consumer.core.domain.message.Message;
import jakarta.inject.Named;

@Named
public class MessageAppMapper {

    private final MessageIdAppMapper idMapper;

    public MessageAppMapper(MessageIdAppMapper idMapper) {
        this.idMapper = idMapper;
    }

    public MessageOutputAppVo entityToOutput(Message message) {
        if (message == null) {
            return null;
        }

        return new MessageOutputAppVo(
            idMapper.toAppId(message.id()),
            message.createdAt(),
            message.message(),
            message.modificationCounter()
        );
    }

    public Message inputToEntity(MessageInputEventAppVo message) {
        if (message == null) {
            return null;
        }

        return new Message(
            idMapper.toId(message.id()),
            message.createdAt(),
            message.message(),
            null
        );
    }
}

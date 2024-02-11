package cmahy.brokers.consumer.core.adapter.message.mapper;

import cmahy.brokers.consumer.core.application.message.vo.input.MessageInputEventAppVo;
import cmahy.brokers.consumer.message.event.vo.input.MessageInputEventVo;
import jakarta.inject.Named;

@Named
public class MessageInputAdapterMapper {

    private final MessageIdAdapterMapper idMapper;

    public MessageInputAdapterMapper(MessageIdAdapterMapper idMapper) {
        this.idMapper = idMapper;
    }

    public MessageInputEventAppVo toAppVo(MessageInputEventVo input) {
        if (input == null) {
            return null;
        }

        return new MessageInputEventAppVo(
            idMapper.toAppId(input.id()),
            input.message(),
            input.createdAt()
        );
    }
}

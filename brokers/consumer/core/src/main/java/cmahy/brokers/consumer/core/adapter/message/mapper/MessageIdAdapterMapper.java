package cmahy.brokers.consumer.core.adapter.message.mapper;

import cmahy.brokers.consumer.message.api.vo.id.MessageApiId;
import cmahy.brokers.consumer.core.application.message.vo.id.MessageAppId;
import cmahy.brokers.consumer.message.event.vo.id.MessageEventId;
import jakarta.inject.Named;

@Named
public class MessageIdAdapterMapper {

    public MessageAppId toAppId(MessageEventId source) {
        if (source == null || source.value() == null) {
            return null;
        }

        return new MessageAppId(source.value());
    }

    public MessageApiId toApiId(MessageAppId source) {
        if (source == null || source.value() == null) {
            return null;
        }

        return new MessageApiId(source.value());
    }
}

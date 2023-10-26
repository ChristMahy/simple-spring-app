package cmahy.brokers.consumer.core.adapter.mapper.message;

import cmahy.brokers.consumer.api.vo.id.MessageApiId;
import cmahy.brokers.consumer.core.application.vo.id.MessageAppId;
import cmahy.brokers.consumer.event.vo.id.MessageEventId;
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

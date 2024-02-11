package cmahy.brokers.consumer.core.application.message.mapper;

import cmahy.brokers.consumer.core.application.message.vo.id.MessageAppId;
import jakarta.inject.Named;

@Named
public class MessageIdAppMapper {

    public MessageAppId toAppId(Long id) {
        if (id == null) {
            return null;
        }

        return new MessageAppId(id);
    }

    public Long toId(MessageAppId id) {
        return id == null ? null : id.value();
    }
}

package cmahy.brokers.consumer.core.application.mapper.message;

import cmahy.brokers.consumer.core.application.vo.id.MessageAppId;
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

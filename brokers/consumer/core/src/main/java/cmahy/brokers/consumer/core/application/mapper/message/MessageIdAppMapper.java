package cmahy.brokers.consumer.core.application.mapper.message;

import cmahy.brokers.consumer.core.application.vo.id.MessageAppId;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface MessageIdAppMapper {

    default MessageAppId toAppId(Long id) {
        return new MessageAppId(id);
    }

    default Long toEntityId(MessageAppId id) {
        if (id != null) {
            return id.value();
        }

        return null;
    }
}

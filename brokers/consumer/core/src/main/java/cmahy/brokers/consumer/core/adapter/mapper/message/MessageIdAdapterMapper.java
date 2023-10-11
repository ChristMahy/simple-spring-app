package cmahy.brokers.consumer.core.adapter.mapper.message;

import cmahy.brokers.consumer.core.application.vo.id.MessageAppId;
import cmahy.brokers.consumer.event.vo.id.MessageEventId;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface MessageIdAdapterMapper {

    default MessageAppId toAppId(MessageEventId source) {
        if (source == null || source.value() == null) {
            return null;
        }

        return new MessageAppId(source.value());
    }
}

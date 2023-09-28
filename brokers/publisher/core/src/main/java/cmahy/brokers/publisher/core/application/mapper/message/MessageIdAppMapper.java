package cmahy.brokers.publisher.core.application.mapper.message;

import cmahy.brokers.publisher.core.application.vo.id.MessageAppId;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface MessageIdAppMapper {

    default MessageAppId toAppId(Long id) {
        return new MessageAppId(id);
    }
}

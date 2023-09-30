package cmahy.brokers.consumer.core.adapter.mapper.message;

import cmahy.brokers.consumer.api.vo.input.MessageInputApiVo;
import cmahy.brokers.consumer.core.application.vo.input.MessageInputAppVo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface MessageInputAdapterMapper {

    MessageInputAppVo toAppVo(MessageInputApiVo input);
}

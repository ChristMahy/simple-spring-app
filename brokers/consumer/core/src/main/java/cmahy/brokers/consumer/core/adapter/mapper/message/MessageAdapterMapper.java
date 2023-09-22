package cmahy.brokers.consumer.core.adapter.mapper.message;

import cmahy.brokers.consumer.core.application.vo.output.MessageOutputAppVo;
import cmahy.brokers.consumer.vo.output.MessageOutputApiVo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface MessageAdapterMapper {

    MessageOutputApiVo toApiVo(MessageOutputAppVo output);
}

package cmahy.brokers.publisher.core.adapter.mapper.message;

import cmahy.brokers.publisher.api.vo.output.MessageOutputApiVo;
import cmahy.brokers.publisher.core.application.vo.output.MessageOutputAppVo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface MessageAdapterMapper {

    MessageOutputApiVo toApiVo(MessageOutputAppVo output);
}

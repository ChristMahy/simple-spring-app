package cmahy.simple.spring.brokers.publisher.core.adapter.mapper.message;

import cmahy.brokers.publisher.api.vo.input.MessageInputApiVo;
import cmahy.simple.spring.brokers.publisher.core.application.vo.input.MessageInputAppVo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface MessageInputAdapterMapper {

    MessageInputAppVo toAppVo(MessageInputApiVo source);
}

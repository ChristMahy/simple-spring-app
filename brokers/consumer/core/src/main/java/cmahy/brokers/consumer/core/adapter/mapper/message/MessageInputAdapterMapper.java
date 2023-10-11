package cmahy.brokers.consumer.core.adapter.mapper.message;

import cmahy.brokers.consumer.core.application.vo.input.MessageInputEventAppVo;
import cmahy.brokers.consumer.event.vo.input.MessageInputEventVo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    uses = { MessageIdAdapterMapper.class }
)
public interface MessageInputAdapterMapper {

    MessageInputEventAppVo toAppVo(MessageInputEventVo input);
}

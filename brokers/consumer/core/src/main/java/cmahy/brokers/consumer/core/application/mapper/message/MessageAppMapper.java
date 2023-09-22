package cmahy.brokers.consumer.core.application.mapper.message;

import cmahy.brokers.consumer.core.application.vo.output.MessageOutputAppVo;
import cmahy.brokers.consumer.core.domain.Message;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    uses = { MessageIdAppMapper.class }
)
public interface MessageAppMapper {

    MessageOutputAppVo entityToOutput(Message message);
}

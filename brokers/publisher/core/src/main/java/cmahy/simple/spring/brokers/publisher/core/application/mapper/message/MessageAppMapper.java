package cmahy.simple.spring.brokers.publisher.core.application.mapper.message;

import cmahy.simple.spring.brokers.publisher.core.application.vo.input.MessageInputAppVo;
import cmahy.simple.spring.brokers.publisher.core.application.vo.output.MessageOutputAppVo;
import cmahy.simple.spring.brokers.publisher.core.domain.Message;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    uses = { MessageIdAppMapper.class }
)
public interface MessageAppMapper {

    MessageOutputAppVo entityToOutput(Message message);

    Message inputToEntity(MessageInputAppVo input);
}

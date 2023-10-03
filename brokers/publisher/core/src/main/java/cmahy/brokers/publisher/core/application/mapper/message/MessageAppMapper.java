package cmahy.brokers.publisher.core.application.mapper.message;

import cmahy.brokers.publisher.core.application.vo.input.MessageInputAppVo;
import cmahy.brokers.publisher.core.application.vo.output.MessageOutputAppVo;
import cmahy.brokers.publisher.core.domain.Message;
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

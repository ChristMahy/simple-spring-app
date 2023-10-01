package cmahy.brokers.consumer.core.application.mapper.message;

import cmahy.brokers.consumer.core.application.vo.input.MessageInputAppVo;
import cmahy.brokers.consumer.core.application.vo.output.MessageOutputAppVo;
import cmahy.brokers.consumer.core.domain.Message;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    uses = { MessageIdAppMapper.class }
)
public interface MessageAppMapper {

    MessageOutputAppVo entityToOutput(Message message);

    @Mapping(target = "id", ignore = true)
    Message inputToEntity(MessageInputAppVo message);
}

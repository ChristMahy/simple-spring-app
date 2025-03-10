package cmahy.simple.spring.brokers.consumer.core.application.message.mapper;

import cmahy.simple.spring.brokers.consumer.core.application.message.vo.id.MessageAppId;
import cmahy.simple.spring.brokers.consumer.core.application.message.vo.input.MessageInputEventAppVo;
import cmahy.simple.spring.brokers.consumer.core.application.message.vo.output.MessageOutputAppVo;
import cmahy.simple.spring.brokers.consumer.core.domain.message.Message;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static cmahy.simple.spring.common.helper.Generator.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageAppMapperTest {

    @Mock
    private MessageIdAppMapper idMapper;

    @InjectMocks
    private MessageAppMapper mapper;

    @Test
    void entityToOutput() {
        final Long id = randomLongEqualOrAboveZero();
        final MessageAppId appId = mock(MessageAppId.class);
        final Message message = new Message(
            id,
            LocalDateTime.now(),
            generateAString(),
            randomIntEqualOrAboveZero()
        );

        when(idMapper.toAppId(id)).thenReturn(appId);

        final MessageOutputAppVo actual = mapper.entityToOutput(message);

        assertThat(actual).isNotNull();
        assertThat(actual.id()).isEqualTo(appId);
        assertThat(actual.createdAt()).isEqualTo(message.createdAt());
        assertThat(actual.message()).isEqualTo(message.message());
        assertThat(actual.modificationCounter()).isEqualTo(message.modificationCounter());
    }

    @Test
    void entityToOutput_whenSourceIsNull_thenResultIsNull() {
        assertThat(mapper.entityToOutput(null)).isNull();
    }

    @Test
    void inputToEntity() {
        final Long id = randomLongEqualOrAboveZero();
        final MessageAppId appId = mock(MessageAppId.class);
        final MessageInputEventAppVo message = new MessageInputEventAppVo(
            appId,
            generateAString(),
            LocalDateTime.now()
        );

        when(idMapper.toId(appId)).thenReturn(id);

        final Message actual = mapper.inputToEntity(message);

        assertThat(actual).isNotNull();
        assertThat(actual.id()).isEqualTo(id);
        assertThat(actual.createdAt()).isEqualTo(message.createdAt());
        assertThat(actual.message()).isEqualTo(message.message());
        assertThat(actual.modificationCounter()).isNull();
    }

    @Test
    void inputToEntity_whenSourceIsNull_thenResultIsNull() {
        assertThat(mapper.inputToEntity(null)).isNull();
    }
}
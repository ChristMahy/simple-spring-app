package cmahy.simple.spring.brokers.consumer.core.adapter.message.mapper;

import cmahy.simple.spring.brokers.consumer.core.application.message.vo.id.MessageAppId;
import cmahy.simple.spring.brokers.consumer.core.application.message.vo.input.MessageInputEventAppVo;
import cmahy.simple.spring.brokers.consumer.message.event.vo.id.MessageEventId;
import cmahy.simple.spring.brokers.consumer.message.event.vo.input.MessageInputEventVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static cmahy.simple.spring.common.helper.Generator.generateAString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageInputAdapterMapperTest {

    @Mock
    private MessageIdAdapterMapper idMapper;

    @InjectMocks
    private MessageInputAdapterMapper mapper;

    @Mock
    private MessageEventId eventId;

    @Mock
    private MessageAppId appId;

    @Test
    void toAppVo() {
        final MessageInputEventVo eventVo = new MessageInputEventVo(
            eventId,
            generateAString(),
            LocalDateTime.now()
        );

        when(idMapper.toAppId(eventId)).thenReturn(appId);

        final MessageInputEventAppVo actual = mapper.toAppVo(eventVo);

        assertThat(actual).isNotNull();

        assertThat(actual.id()).isEqualTo(appId);
        assertThat(actual.createdAt()).isEqualTo(eventVo.createdAt());
        assertThat(actual.message()).isEqualTo(eventVo.message());
    }

    @Test
    void toAppVo_whenGivenIsNull_thenResultNull() {
        assertThat(mapper.toAppVo(null)).isNull();
    }
}
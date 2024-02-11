package cmahy.brokers.consumer.core.adapter.message.mapper;

import cmahy.brokers.consumer.core.application.message.vo.id.MessageAppId;
import cmahy.brokers.consumer.core.application.message.vo.output.MessageOutputAppVo;
import cmahy.brokers.consumer.message.api.vo.id.MessageApiId;
import cmahy.brokers.consumer.message.api.vo.output.MessageOutputApiVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static cmahy.common.helper.Generator.generateAString;
import static cmahy.common.helper.Generator.randomInt;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageOutputAdapterMapperTest {

    @Mock
    private MessageIdAdapterMapper idMapper;

    @InjectMocks
    private MessageOutputAdapterMapper mapper;

    @Test
    void toApiVo() {
        final MessageAppId appId = mock(MessageAppId.class);
        final MessageApiId apiId = mock(MessageApiId.class);

        when(idMapper.toApiId(appId)).thenReturn(apiId);

        final MessageOutputAppVo appVo = new MessageOutputAppVo(
            appId,
            LocalDateTime.now(),
            generateAString(),
            randomInt(0, Integer.MAX_VALUE)
        );

        MessageOutputApiVo actual = mapper.toApiVo(appVo);

        assertThat(actual).isNotNull();
        assertThat(actual.id()).isEqualTo(apiId);
        assertThat(actual.message()).isEqualTo(appVo.message());
        assertThat(actual.createdAt()).isEqualTo(appVo.createdAt());
        assertThat(actual.modificationCounter()).isEqualTo(appVo.modificationCounter());
    }

    @Test
    void toApiVo_whenNull_thenResultIsNull() {
        assertThat(mapper.toApiVo(null)).isNull();
    }
}
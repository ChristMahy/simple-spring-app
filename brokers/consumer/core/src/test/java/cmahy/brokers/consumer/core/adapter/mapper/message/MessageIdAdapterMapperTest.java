package cmahy.brokers.consumer.core.adapter.mapper.message;

import cmahy.brokers.consumer.api.vo.id.MessageApiId;
import cmahy.brokers.consumer.core.application.vo.id.MessageAppId;
import cmahy.brokers.consumer.event.vo.id.MessageEventId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static cmahy.common.helper.Generator.randomLongEqualOrAboveZero;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class MessageIdAdapterMapperTest {

    @InjectMocks
    private MessageIdAdapterMapper mapper;

    @Test
    void toAppId() {
        final MessageEventId eventId = new MessageEventId(randomLongEqualOrAboveZero());

        final MessageAppId actual = mapper.toAppId(eventId);

        assertThat(actual).isNotNull();
        assertThat(actual.value()).isEqualTo(eventId.value());
    }

    @Test
    void toAppId_whenValueIsNull_thenResultIsNull() {
        final MessageEventId eventId = new MessageEventId(null);

        assertThat(mapper.toAppId(eventId)).isNull();
    }

    @Test
    void toAppId_whenGivenObjectIsNull_thenResultIsNull() {
        assertThat(mapper.toAppId(null)).isNull();
    }

    @Test
    void toApiId() {
        final MessageAppId appId = new MessageAppId(randomLongEqualOrAboveZero());

        final MessageApiId actual = mapper.toApiId(appId);

        assertThat(actual).isNotNull();
        assertThat(actual.value()).isEqualTo(appId.value());
    }

    @Test
    void toApiId_whenValueIsNull_thenResultIsNull() {
        final MessageAppId appId = new MessageAppId(null);

        assertThat(mapper.toApiId(appId)).isNull();
    }

    @Test
    void toApiId_whenGivenObjectIsNull_thenResultIsNull() {
        assertThat(mapper.toApiId(null)).isNull();
    }
}
package cmahy.brokers.consumer.core.application.mapper.message;

import cmahy.brokers.consumer.core.application.vo.id.MessageAppId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static cmahy.common.helper.Generator.randomLong;
import static cmahy.common.helper.Generator.randomLongEqualOrAboveZero;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MessageIdAppMapperTest {

    @InjectMocks
    private MessageIdAppMapper mapper;

    @Test
    void toAppId() {
        final Long id = randomLongEqualOrAboveZero();

        final MessageAppId actual = mapper.toAppId(id);

        assertThat(actual).isNotNull();
        assertThat(actual.value()).isEqualTo(id);
    }

    @Test
    void toAppId_whenSourceIsNull_thenReturnNull() {
        final MessageAppId actual = mapper.toAppId(null);

        assertThat(actual).isNull();
    }

    @Test
    void toId() {
        final MessageAppId id = new MessageAppId(randomLongEqualOrAboveZero());

        final Long actual = mapper.toId(id);

        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(id.value());
    }

    @Test
    void toId_whenSourceIsNull_thenResultIsNull() {
        assertThat(mapper.toId(null)).isNull();
    }
}
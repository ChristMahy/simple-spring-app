package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.id.codec.type;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.user.kernel.domain.id.UserId;
import com.datastax.dse.driver.api.core.DseProtocolVersion;
import com.datastax.oss.driver.api.core.DefaultProtocolVersion;
import com.datastax.oss.driver.api.core.ProtocolVersion;
import com.datastax.oss.driver.api.core.type.codec.TypeCodecs;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
class UserIdTypeCodecTest {

    @InjectMocks
    private UserIdTypeCodec userIdTypeCodec;

    @Test
    void getJavaType() {
        assertDoesNotThrow(() -> {
            assertThat(userIdTypeCodec.getJavaType()).isNotNull();
        });
    }

    @Test
    void getCqlType() {
        assertDoesNotThrow(() -> {
            assertThat(userIdTypeCodec.getCqlType()).isNotNull();
        });
    }

    @ParameterizedTest
    @MethodSource("protocolVersions")
    void encode(ProtocolVersion protocolVersion) {
        assertDoesNotThrow(() -> {
            UserId userId = new UserId(Generator.randomUUID());

            ByteBuffer actual = userIdTypeCodec.encode(userId, protocolVersion);

            assertThat(actual).isNotNull();

            assertThat(TypeCodecs.UUID.decode(actual, protocolVersion)).isEqualTo(userId.value());
        });
    }

    @ParameterizedTest
    @MethodSource("protocolVersions")
    void encode_whenUserIdIsNull_thenReturnNull(ProtocolVersion protocolVersion) {
        assertDoesNotThrow(() -> {
            assertThat(userIdTypeCodec.encode(null, protocolVersion)).isNull();
        });
    }

    @ParameterizedTest
    @MethodSource("protocolVersions")
    void decode(ProtocolVersion protocolVersion) {
        assertDoesNotThrow(() -> {
            UserId userId = new UserId(Generator.randomUUID());
            ByteBuffer byteBuffer = TypeCodecs.UUID.encode(userId.value(), protocolVersion);

            UserId actual = userIdTypeCodec.decode(byteBuffer, protocolVersion);

            assertThat(actual).isNotNull();

            assertThat(actual.value()).isEqualTo(userId.value());
        });
    }

    @ParameterizedTest
    @MethodSource("protocolVersions")
    void decode_whenUserIdIsNull_thenReturnNull(ProtocolVersion protocolVersion) {
        assertDoesNotThrow(() -> {
            assertThat(userIdTypeCodec.decode(null, protocolVersion)).isNull();
        });
    }

    private static Stream<ProtocolVersion> protocolVersions() {
        return Stream.concat(
            Arrays.stream(DefaultProtocolVersion.values()),
            Arrays.stream(DseProtocolVersion.values())
        );
    }

    @Test
    void format() {
        assertDoesNotThrow(() -> {
            UserId userId = new UserId(Generator.randomUUID());

            String actual = userIdTypeCodec.format(userId);

            assertThat(actual)
                .isNotNull()
                .isEqualTo(userId.value().toString());
        });
    }

    @Test
    void format_whenUserIdIsNull_thenReturnEmptyString() {
        assertDoesNotThrow(() -> {
            assertThat(userIdTypeCodec.format(null)).isEmpty();
        });
    }

    @Test
    void parse() {
        assertDoesNotThrow(() -> {
            UserId userId = new UserId(Generator.randomUUID());

            UserId actual = userIdTypeCodec.parse(userId.value().toString());

            assertThat(actual)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(userId);
        });
    }

    @Test
    void parse_whenUserIdIsNull_thenReturnNull() {
        assertDoesNotThrow(() -> {
            assertThat(userIdTypeCodec.parse(null)).isNull();
        });
    }
}
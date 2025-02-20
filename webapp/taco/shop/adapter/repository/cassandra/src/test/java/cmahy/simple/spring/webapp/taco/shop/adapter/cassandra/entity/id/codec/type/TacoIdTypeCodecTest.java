package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.id.codec.type;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.id.TacoId;
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
class TacoIdTypeCodecTest {

    @InjectMocks
    private TacoIdTypeCodec tacoIdTypeCodec;
    
    @Test
    void getJavaType() {
        assertDoesNotThrow(() -> {
            assertThat(tacoIdTypeCodec.getJavaType()).isNotNull();
        });
    }

    @Test
    void getCqlType() {
        assertDoesNotThrow(() -> {
            assertThat(tacoIdTypeCodec.getCqlType()).isNotNull();
        });
    }

    @ParameterizedTest
    @MethodSource("protocolVersions")
    void encode(ProtocolVersion protocolVersion) {
        assertDoesNotThrow(() -> {
            TacoId tacoId = new TacoId(Generator.randomUUID());

            ByteBuffer actual = tacoIdTypeCodec.encode(tacoId, protocolVersion);

            assertThat(actual).isNotNull();

            assertThat(TypeCodecs.UUID.decode(actual, protocolVersion)).isEqualTo(tacoId.value());
        });
    }

    @ParameterizedTest
    @MethodSource("protocolVersions")
    void encode_whenRoleIdIsNull_thenReturnNull(ProtocolVersion protocolVersion) {
        assertDoesNotThrow(() -> {
            assertThat(tacoIdTypeCodec.encode(null, protocolVersion)).isNull();
        });
    }

    @ParameterizedTest
    @MethodSource("protocolVersions")
    void decode(ProtocolVersion protocolVersion) {
        assertDoesNotThrow(() -> {
            TacoId tacoId = new TacoId(Generator.randomUUID());
            ByteBuffer byteBuffer = TypeCodecs.UUID.encode(tacoId.value(), protocolVersion);

            TacoId actual = tacoIdTypeCodec.decode(byteBuffer, protocolVersion);

            assertThat(actual).isNotNull();

            assertThat(actual.value()).isEqualTo(tacoId.value());
        });
    }

    @ParameterizedTest
    @MethodSource("protocolVersions")
    void decode_whenRoleIdIsNull_thenReturnNull(ProtocolVersion protocolVersion) {
        assertDoesNotThrow(() -> {
            assertThat(tacoIdTypeCodec.decode(null, protocolVersion)).isNull();
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
            TacoId tacoId = new TacoId(Generator.randomUUID());

            String actual = tacoIdTypeCodec.format(tacoId);

            assertThat(actual)
                .isNotNull()
                .isEqualTo(tacoId.value().toString());
        });
    }

    @Test
    void format_whenRoleIdIsNull_thenReturnEmptyString() {
        assertDoesNotThrow(() -> {
            assertThat(tacoIdTypeCodec.format(null)).isEmpty();
        });
    }

    @Test
    void parse() {
        assertDoesNotThrow(() -> {
            TacoId tacoId = new TacoId(Generator.randomUUID());

            TacoId actual = tacoIdTypeCodec.parse(tacoId.value().toString());

            assertThat(actual)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(tacoId);
        });
    }

    @Test
    void parse_whenRoleIdIsNull_thenReturnNull() {
        assertDoesNotThrow(() -> {
            assertThat(tacoIdTypeCodec.parse(null)).isNull();
        });
    }
}
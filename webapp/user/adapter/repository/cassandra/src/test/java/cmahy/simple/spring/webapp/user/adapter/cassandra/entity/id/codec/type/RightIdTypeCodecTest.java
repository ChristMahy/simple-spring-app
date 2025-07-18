package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.id.codec.type;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.user.kernel.domain.id.RightId;
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
class RightIdTypeCodecTest {

    @InjectMocks
    private RightIdTypeCodec rightIdTypeCodec;

    @Test
    void getJavaType() {
        assertDoesNotThrow(() -> {


            assertThat(rightIdTypeCodec.getJavaType()).isNotNull();


        });
    }

    @Test
    void getCqlType() {
        assertDoesNotThrow(() -> {


            assertThat(rightIdTypeCodec.getCqlType()).isNotNull();


        });
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("protocolVersions")
    void encode(ProtocolVersion protocolVersion) {
        assertDoesNotThrow(() -> {

            RightId rightId = new RightId(Generator.randomUUID());


            ByteBuffer actual = rightIdTypeCodec.encode(rightId, protocolVersion);


            assertThat(actual).isNotNull();

            assertThat(TypeCodecs.UUID.decode(actual, protocolVersion)).isEqualTo(rightId.value());

        });
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("protocolVersions")
    void encode_whenUserIdIsNull_thenReturnNull(ProtocolVersion protocolVersion) {
        assertDoesNotThrow(() -> {


            assertThat(rightIdTypeCodec.encode(null, protocolVersion)).isNull();


        });
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("protocolVersions")
    void decode(ProtocolVersion protocolVersion) {
        assertDoesNotThrow(() -> {

            RightId rightId = new RightId(Generator.randomUUID());
            ByteBuffer byteBuffer = TypeCodecs.UUID.encode(rightId.value(), protocolVersion);


            RightId actual = rightIdTypeCodec.decode(byteBuffer, protocolVersion);


            assertThat(actual).isNotNull();

            assertThat(actual.value()).isEqualTo(rightId.value());

        });
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("protocolVersions")
    void decode_whenUserIdIsNull_thenReturnNull(ProtocolVersion protocolVersion) {
        assertDoesNotThrow(() -> {


            assertThat(rightIdTypeCodec.decode(null, protocolVersion)).isNull();


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

            RightId rightId = new RightId(Generator.randomUUID());


            String actual = rightIdTypeCodec.format(rightId);

            assertThat(actual)
                .isNotNull()
                .isEqualTo(rightId.value().toString());

        });
    }

    @Test
    void format_whenUserIdIsNull_thenReturnEmptyString() {
        assertDoesNotThrow(() -> {


            assertThat(rightIdTypeCodec.format(null)).isEmpty();


        });
    }

    @Test
    void parse() {
        assertDoesNotThrow(() -> {

            RightId rightId = new RightId(Generator.randomUUID());


            RightId actual = rightIdTypeCodec.parse(rightId.value().toString());


            assertThat(actual)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(rightId);

        });
    }

    @Test
    void parse_whenUserIdIsNull_thenReturnNull() {
        assertDoesNotThrow(() -> {


            assertThat(rightIdTypeCodec.parse(null)).isNull();


        });
    }
}
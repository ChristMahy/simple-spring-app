package cmahy.webapp.user.adapter.cassandra.entity.id.codec.type;

import cmahy.common.helper.Generator;
import cmahy.webapp.user.kernel.domain.id.RoleId;
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
class RoleIdTypeCodecTest {

    @InjectMocks
    private RoleIdTypeCodec roleIdTypeCodec;

    @Test
    void getJavaType() {
        assertDoesNotThrow(() -> {
            assertThat(roleIdTypeCodec.getJavaType()).isNotNull();
        });
    }

    @Test
    void getCqlType() {
        assertDoesNotThrow(() -> {
            assertThat(roleIdTypeCodec.getCqlType()).isNotNull();
        });
    }

    @ParameterizedTest
    @MethodSource("protocolVersions")
    void encode(ProtocolVersion protocolVersion) {
        assertDoesNotThrow(() -> {
            RoleId roleId = new RoleId(Generator.randomUUID());

            ByteBuffer actual = roleIdTypeCodec.encode(roleId, protocolVersion);

            assertThat(actual).isNotNull();

            assertThat(TypeCodecs.UUID.decode(actual, protocolVersion)).isEqualTo(roleId.value());
        });
    }

    @ParameterizedTest
    @MethodSource("protocolVersions")
    void encode_whenRoleIdIsNull_thenReturnNull(ProtocolVersion protocolVersion) {
        assertDoesNotThrow(() -> {
            assertThat(roleIdTypeCodec.encode(null, protocolVersion)).isNull();
        });
    }

    @ParameterizedTest
    @MethodSource("protocolVersions")
    void decode(ProtocolVersion protocolVersion) {
        assertDoesNotThrow(() -> {
            RoleId roleId = new RoleId(Generator.randomUUID());
            ByteBuffer byteBuffer = TypeCodecs.UUID.encode(roleId.value(), protocolVersion);

            RoleId actual = roleIdTypeCodec.decode(byteBuffer, protocolVersion);

            assertThat(actual).isNotNull();

            assertThat(actual.value()).isEqualTo(roleId.value());
        });
    }

    @ParameterizedTest
    @MethodSource("protocolVersions")
    void decode_whenRoleIdIsNull_thenReturnNull(ProtocolVersion protocolVersion) {
        assertDoesNotThrow(() -> {
            assertThat(roleIdTypeCodec.decode(null, protocolVersion)).isNull();
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
            RoleId roleId = new RoleId(Generator.randomUUID());

            String actual = roleIdTypeCodec.format(roleId);

            assertThat(actual)
                .isNotNull()
                .isEqualTo(roleId.value().toString());
        });
    }

    @Test
    void format_whenRoleIdIsNull_thenReturnEmptyString() {
        assertDoesNotThrow(() -> {
            assertThat(roleIdTypeCodec.format(null)).isEmpty();
        });
    }

    @Test
    void parse() {
        assertDoesNotThrow(() -> {
            RoleId roleId = new RoleId(Generator.randomUUID());

            RoleId actual = roleIdTypeCodec.parse(roleId.value().toString());

            assertThat(actual)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(roleId);
        });
    }

    @Test
    void parse_whenRoleIdIsNull_thenReturnNull() {
        assertDoesNotThrow(() -> {
            assertThat(roleIdTypeCodec.parse(null)).isNull();
        });
    }
}
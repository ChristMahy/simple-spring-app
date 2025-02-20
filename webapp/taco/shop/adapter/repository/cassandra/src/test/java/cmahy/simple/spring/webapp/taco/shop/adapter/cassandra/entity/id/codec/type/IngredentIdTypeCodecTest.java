package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.id.codec.type;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.id.IngredientId;
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
class IngredentIdTypeCodecTest {

    @InjectMocks
    private IngredentIdTypeCodec ingredentIdTypeCodec;

    @Test
    void getJavaType() {
        assertDoesNotThrow(() -> {
            assertThat(ingredentIdTypeCodec.getJavaType()).isNotNull();
        });
    }

    @Test
    void getCqlType() {
        assertDoesNotThrow(() -> {
            assertThat(ingredentIdTypeCodec.getCqlType()).isNotNull();
        });
    }

    @ParameterizedTest
    @MethodSource("protocolVersions")
    void encode(ProtocolVersion protocolVersion) {
        assertDoesNotThrow(() -> {
            IngredientId ingredientId = new IngredientId(Generator.randomUUID());

            ByteBuffer actual = ingredentIdTypeCodec.encode(ingredientId, protocolVersion);

            assertThat(actual).isNotNull();

            assertThat(TypeCodecs.UUID.decode(actual, protocolVersion)).isEqualTo(ingredientId.value());
        });
    }

    @ParameterizedTest
    @MethodSource("protocolVersions")
    void encode_whenRoleIdIsNull_thenReturnNull(ProtocolVersion protocolVersion) {
        assertDoesNotThrow(() -> {
            assertThat(ingredentIdTypeCodec.encode(null, protocolVersion)).isNull();
        });
    }

    @ParameterizedTest
    @MethodSource("protocolVersions")
    void decode(ProtocolVersion protocolVersion) {
        assertDoesNotThrow(() -> {
            IngredientId ingredientId = new IngredientId(Generator.randomUUID());
            ByteBuffer byteBuffer = TypeCodecs.UUID.encode(ingredientId.value(), protocolVersion);

            IngredientId actual = ingredentIdTypeCodec.decode(byteBuffer, protocolVersion);

            assertThat(actual).isNotNull();

            assertThat(actual.value()).isEqualTo(ingredientId.value());
        });
    }

    @ParameterizedTest
    @MethodSource("protocolVersions")
    void decode_whenRoleIdIsNull_thenReturnNull(ProtocolVersion protocolVersion) {
        assertDoesNotThrow(() -> {
            assertThat(ingredentIdTypeCodec.decode(null, protocolVersion)).isNull();
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
            IngredientId ingredientId = new IngredientId(Generator.randomUUID());

            String actual = ingredentIdTypeCodec.format(ingredientId);

            assertThat(actual)
                .isNotNull()
                .isEqualTo(ingredientId.value().toString());
        });
    }

    @Test
    void format_whenRoleIdIsNull_thenReturnEmptyString() {
        assertDoesNotThrow(() -> {
            assertThat(ingredentIdTypeCodec.format(null)).isEmpty();
        });
    }

    @Test
    void parse() {
        assertDoesNotThrow(() -> {
            IngredientId ingredientId = new IngredientId(Generator.randomUUID());

            IngredientId actual = ingredentIdTypeCodec.parse(ingredientId.value().toString());

            assertThat(actual)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(ingredientId);
        });
    }

    @Test
    void parse_whenRoleIdIsNull_thenReturnNull() {
        assertDoesNotThrow(() -> {
            assertThat(ingredentIdTypeCodec.parse(null)).isNull();
        });
    }
}
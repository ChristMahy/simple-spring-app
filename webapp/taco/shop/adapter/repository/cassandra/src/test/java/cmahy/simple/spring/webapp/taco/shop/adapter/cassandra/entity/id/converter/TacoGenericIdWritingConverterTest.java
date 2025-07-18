package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.id.converter;

import cmahy.simple.spring.common.entity.id.EntityId;
import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.id.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TacoGenericIdWritingConverterTest {

    @InjectMocks
    private TacoGenericIdWritingConverter tacoGenericIdWritingConverter;

    @Test
    void getConvertibleTypes() {
        assertDoesNotThrow(() -> {

            Set<GenericConverter.ConvertiblePair> actual = tacoGenericIdWritingConverter.getConvertibleTypes();


            assertThat(actual)
                .isNotEmpty()
                .satisfies(convertibleTypes -> {
                    assertThat(
                        convertibleTypes.stream()
                            .allMatch(convertibleType -> Objects.equals(convertibleType.getTargetType(), UUID.class))
                    ).isTrue();
                });
        });
    }

    @ParameterizedTest(name = "Convert <{0}> => <UUID>")
    @MethodSource("convertSource")
    void convert(String klassName, Class<EntityId<UUID>> klass, EntityId<UUID> id) {
        assertDoesNotThrow(() -> {

            Object actual = tacoGenericIdWritingConverter.convert(id, TypeDescriptor.valueOf(klass), TypeDescriptor.valueOf(UUID.class));


            assertThat(actual)
                .isNotNull()
                .isInstanceOf(UUID.class)
                .isEqualTo(id.value());
        });
    }

    private static Stream<Arguments> convertSource() {
        return Stream.of(
            Arguments.of(ClientOrderId.class.getSimpleName(), ClientOrderId.class, new ClientOrderId(Generator.randomUUID())),
            Arguments.of(IngredientId.class.getSimpleName(), IngredientId.class, new IngredientId(Generator.randomUUID())),
            Arguments.of(TacoId.class.getSimpleName(), TacoId.class, new TacoId(Generator.randomUUID()))
        );
    }

    @Test
    void convert_whenUnexpectedClass_thenThrowError() {
        Class<?> classUnexpected = Object.class;

        RuntimeException actualException = assertThrows(RuntimeException.class, () -> {

            tacoGenericIdWritingConverter.convert(Generator.randomUUID(), TypeDescriptor.valueOf(classUnexpected), TypeDescriptor.valueOf(UUID.class));

        });


        assertThat(actualException.getMessage())
            .startsWith("Unsupported type")
            .contains(classUnexpected.getSimpleName());
    }

    @Test
    void convert_whenSourceIsNull_thenReturnNull() {
        assertDoesNotThrow(() -> {

            Class<?> aClass = Object.class;

            assertThat(tacoGenericIdWritingConverter.convert(null, TypeDescriptor.valueOf(aClass), TypeDescriptor.valueOf(UUID.class))).isNull();

        });
    }
}
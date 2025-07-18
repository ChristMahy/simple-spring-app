package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.id.converter;

import cmahy.simple.spring.common.entity.id.EntityId;
import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.user.kernel.domain.id.*;
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
class UserGenericIdWritingConverterTest {

    @InjectMocks
    private UserGenericIdWritingConverter userGenericIdWritingConverter;

    @Test
    void getConvertibleTypes() {
        assertDoesNotThrow(() -> {

            Set<GenericConverter.ConvertiblePair> actual = userGenericIdWritingConverter.getConvertibleTypes();


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

            Object actual = userGenericIdWritingConverter.convert(id, TypeDescriptor.valueOf(klass), TypeDescriptor.valueOf(UUID.class));


            assertThat(actual)
                .isNotNull()
                .isInstanceOf(UUID.class)
                .isEqualTo(id.value());
        });
    }

    private static Stream<Arguments> convertSource() {
        return Stream.of(
            Arguments.of(RightId.class.getSimpleName(), RightId.class, new RightId(Generator.randomUUID())),
            Arguments.of(RoleId.class.getSimpleName(), RoleId.class, new RoleId(Generator.randomUUID())),
            Arguments.of(UserId.class.getSimpleName(), UserId.class, new UserId(Generator.randomUUID()))
        );
    }

    @Test
    void convert_whenUnexpectedClass_thenThrowError() {
        Class<?> classUnexpected = Object.class;

        RuntimeException actualException = assertThrows(RuntimeException.class, () -> {

            userGenericIdWritingConverter.convert(Generator.randomUUID(), TypeDescriptor.valueOf(classUnexpected), TypeDescriptor.valueOf(UUID.class));

        });


        assertThat(actualException.getMessage())
            .startsWith("Unsupported type")
            .contains(classUnexpected.getSimpleName());
    }

    @Test
    void convert_whenSourceIsNull_thenReturnNull() {
        assertDoesNotThrow(() -> {

            Class<?> aClass = Object.class;

            assertThat(userGenericIdWritingConverter.convert(null, TypeDescriptor.valueOf(aClass), TypeDescriptor.valueOf(UUID.class))).isNull();

        });
    }
}
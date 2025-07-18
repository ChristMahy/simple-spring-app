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
class UserGenericIdReadingConverterTest {

    @InjectMocks
    private UserGenericIdReadingConverter userGenericIdReadingConverter;

    @Test
    void getConvertibleTypes() {
        assertDoesNotThrow(() -> {

            Set<GenericConverter.ConvertiblePair> actual = userGenericIdReadingConverter.getConvertibleTypes();


            assertThat(actual)
                .isNotEmpty()
                .satisfies(convertibleTypes -> {
                    assertThat(
                        convertibleTypes.stream()
                            .allMatch(convertibleType -> Objects.equals(convertibleType.getSourceType(), UUID.class))
                    ).isTrue();
                });
        });
    }

    @ParameterizedTest(name = "Convert <UUID> => <{0}>")
    @MethodSource("convertSource")
    void convert(String klassName, Class<EntityId<UUID>> klass, UUID uuid) {
        assertDoesNotThrow(() -> {

            Object actual = userGenericIdReadingConverter.convert(uuid, TypeDescriptor.valueOf(UUID.class), TypeDescriptor.valueOf(klass));


            assertThat(actual)
                .isNotNull()
                .isInstanceOf(klass)
                .isInstanceOf(EntityId.class)
                .extracting(a -> ((EntityId<UUID>) a).value())
                .isEqualTo(uuid);
        });
    }

    private static Stream<Arguments> convertSource() {
        return Stream.of(
            Arguments.of(RightId.class.getSimpleName(), RightId.class, Generator.randomUUID()),
            Arguments.of(RoleId.class.getSimpleName(), RoleId.class, Generator.randomUUID()),
            Arguments.of(UserId.class.getSimpleName(), UserId.class, Generator.randomUUID())
        );
    }

    @Test
    void convert_whenUnexpectedClass_thenThrowError() {
        Class<?> classUnexpected = Object.class;

        RuntimeException actualException = assertThrows(RuntimeException.class, () -> {

            userGenericIdReadingConverter.convert(Generator.randomUUID(), TypeDescriptor.valueOf(UUID.class), TypeDescriptor.valueOf(classUnexpected));

        });


        assertThat(actualException.getMessage())
            .startsWith("Unsupported type")
            .contains(classUnexpected.getSimpleName());
    }

    @Test
    void convert_whenSourceIsNull_thenReturnNull() {
        assertDoesNotThrow(() -> {

            Class<?> aClass = Object.class;

            assertThat(userGenericIdReadingConverter.convert(null, TypeDescriptor.valueOf(UUID.class), TypeDescriptor.valueOf(aClass))).isNull();

        });
    }
}
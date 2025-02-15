package cmahy.simple.spring.common.entity;

import cmahy.simple.spring.common.entity.page.*;
import org.junit.jupiter.api.Test;

import static cmahy.simple.spring.common.helper.Generator.randomInt;
import static cmahy.simple.spring.common.helper.Generator.randomIntEqualOrAboveZero;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EntityPageableImplBuilderTest {

    @Test
    void build() {
        assertDoesNotThrow(() -> {
            Integer defaultPageSize = randomInt(1000, 5000);
            Integer givenPageSize = randomInt(6000, 12000);
            Integer givenPageNumber = randomInt(1, Integer.MAX_VALUE);

            EntityPageable actual = EntityPageableBuilder.<DefaultEntityPageableImpl>instance(defaultPageSize)
                .withPageSize(givenPageSize)
                .withPageNumber(givenPageNumber)
                .build(DefaultEntityPageableImpl.class);

            assertThat(actual).isNotNull();
            assertThat(actual.pageSize()).isEqualTo(givenPageSize);
            assertThat(actual.pageNumber()).isEqualTo(givenPageNumber);
        });
    }

    @Test
    void build_whenNoValueExplicitlySet_thenBuildWithDefaultValue() {
        assertDoesNotThrow(() -> {
            Integer defaultPageSize = randomIntEqualOrAboveZero();

            EntityPageable actual = EntityPageableBuilder.<DefaultEntityPageableImpl>instance(defaultPageSize)
                .build(DefaultEntityPageableImpl.class);

            assertThat(actual).isNotNull();
            assertThat(actual.pageSize()).isEqualTo(defaultPageSize);
            assertThat(actual.pageNumber()).isEqualTo(0);
        });
    }

    @Test
    void build_whenValuesAreNull_thenBuildWithDefaultValues() {
        assertDoesNotThrow(() -> {
            Integer defaultPageSize = randomIntEqualOrAboveZero();

            EntityPageable actual = EntityPageableBuilder.<DefaultEntityPageableImpl>instance(defaultPageSize)
                .withPageSize(null)
                .withPageNumber(null)
                .build(DefaultEntityPageableImpl.class);

            assertThat(actual).isNotNull();
            assertThat(actual.pageSize()).isEqualTo(defaultPageSize);
            assertThat(actual.pageNumber()).isEqualTo(0);
        });
    }

    @Test
    void build_whenGivenValuesAreNegative_thenBuildWithZeroAsValue() {
        assertDoesNotThrow(() -> {
            Integer defaultPageSize = randomIntEqualOrAboveZero();

            EntityPageable actual = EntityPageableBuilder.<DefaultEntityPageableImpl>instance(defaultPageSize)
                .withPageSize(randomInt(Integer.MIN_VALUE, -1))
                .withPageNumber(randomInt(Integer.MIN_VALUE, -1))
                .build(DefaultEntityPageableImpl.class);

            assertThat(actual).isNotNull();
            assertThat(actual.pageSize()).isEqualTo(0);
            assertThat(actual.pageNumber()).isEqualTo(0);
        });
    }

    @Test
    void build_whendefaultPageSizeIsNull_thenBuildWithHardcodedValue() {
        assertDoesNotThrow(() -> {
            EntityPageable actual = EntityPageableBuilder.<DefaultEntityPageableImpl>instance(null)
                .build(DefaultEntityPageableImpl.class);

            assertThat(actual).isNotNull();
            assertThat(actual.pageSize()).isEqualTo(10);
            assertThat(actual.pageNumber()).isEqualTo(0);
        });
    }

    @Test
    void build_whenNoSuitableConstructorFound_thenThrowException() {
        assertThrows(IllegalStateException.class, () -> {
            EntityPageableBuilder.instance(randomIntEqualOrAboveZero())
                .build(EntityPageable.class);
        });
    }
}
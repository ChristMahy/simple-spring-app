package cmahy.simple.spring.common.entity.page;

import org.junit.jupiter.api.Test;

import static cmahy.simple.spring.common.helper.Generator.randomIntEqualOrAboveZero;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class DefaultEntityPageableImplTest {
    @Test
    void testToString() {
        assertDoesNotThrow(() -> {
            EntityPageable entityPageable = new DefaultEntityPageableImpl(
                randomIntEqualOrAboveZero(),
                randomIntEqualOrAboveZero()
            );

            String actual = entityPageable.toString();

            assertThat(actual).isEqualTo(
                String.format("{\"pageNumber\":%d,\"pageSize\":%d}", entityPageable.pageNumber(), entityPageable.pageSize())
            );
        });
    }
}
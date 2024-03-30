package cmahy.common.entity.page;

import cmahy.common.helper.Generator;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static cmahy.common.helper.Generator.randomIntEqualOrAboveZero;
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
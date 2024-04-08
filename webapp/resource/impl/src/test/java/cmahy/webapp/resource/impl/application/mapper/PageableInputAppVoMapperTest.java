package cmahy.webapp.resource.impl.application.mapper;

import cmahy.common.entity.page.EntityPageable;
import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.application.vo.input.PageableInputAppVo;
import cmahy.webapp.resource.impl.exception.NullException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class PageableInputAppVoMapperTest {

    @InjectMocks
    private PageableInputAppVoMapper pageableInputAppVoMapper;

    @Test
    void map() {
        assertDoesNotThrow(() -> {
            PageableInputAppVo input = new PageableInputAppVo(
                Generator.randomIntEqualOrAboveZero(),
                Generator.randomIntEqualOrAboveZero()
            );

            EntityPageable actual = pageableInputAppVoMapper.map(input);

            assertThat(actual)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(input);
        });
    }

    @Test
    void map_whenSourceIsNull_thenThrowNullException() {
        assertThrows(NullException.class, () -> {
            pageableInputAppVoMapper.map(null);
        });
    }
}
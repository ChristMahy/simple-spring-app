package cmahy.webapp.resource.impl.adapter.api.taco.shop.mapper.input;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.api.taco.shop.vo.input.IngredientCreateApiVo;
import cmahy.webapp.resource.impl.application.taco.shop.vo.input.IngredientCreateInputAppVo;
import cmahy.webapp.resource.impl.exception.NullException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class IngredientCreateInputApiMapperTest {

    @InjectMocks
    private IngredientCreateInputApiMapper ingredientCreateInputApiMapper;

    @Test
    void map() {
        assertDoesNotThrow(() -> {
            IngredientCreateApiVo ingredientCreateApiVo = new IngredientCreateApiVo(
                Generator.generateAString(),
                Generator.generateAString()
            );

            IngredientCreateInputAppVo actual = ingredientCreateInputApiMapper.map(ingredientCreateApiVo);

            assertThat(actual)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(ingredientCreateApiVo);
        });
    }

    @Test
    void map_whenGivenValueIsNull_thenThrowNullAssertion() {
        assertThrows(NullException.class, () -> {
            ingredientCreateInputApiMapper.map(null);
        });
    }
}
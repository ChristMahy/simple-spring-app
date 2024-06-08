package cmahy.webapp.resource.impl.adapter.api.taco.shop.mapper.input;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.api.taco.shop.vo.input.IngredientUpdateApiVo;
import cmahy.webapp.resource.impl.application.taco.shop.vo.input.IngredientUpdateInputAppVo;
import cmahy.webapp.resource.impl.exception.NullException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class IngredientUpdateInputApiMapperTest {

    @InjectMocks
    private IngredientUpdateInputApiMapper ingredientUpdateInputApiMapper;

    @Test
    void map() {
        assertDoesNotThrow(() -> {
            IngredientUpdateApiVo ingredientUpdateApiVo = new IngredientUpdateApiVo(
                Optional.of(Generator.generateAString()),
                Optional.of(Generator.generateAString())
            );

            IngredientUpdateInputAppVo actual = ingredientUpdateInputApiMapper.map(ingredientUpdateApiVo);

            assertThat(actual)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(ingredientUpdateApiVo);
        });
    }

    @Test
    void map_whenGivenValueIsNull_thenThrowNullAssertion() {
        assertThrows(NullException.class, () -> {
            ingredientUpdateInputApiMapper.map(null);
        });
    }
}
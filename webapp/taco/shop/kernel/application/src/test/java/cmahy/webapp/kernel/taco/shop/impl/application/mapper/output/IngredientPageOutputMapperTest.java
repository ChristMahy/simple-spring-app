package cmahy.webapp.kernel.taco.shop.impl.application.mapper.output;

import cmahy.common.helper.Generator;
import cmahy.webapp.taco.shop.kernel.application.mapper.output.IngredientOutputMapper;
import cmahy.webapp.taco.shop.kernel.application.mapper.output.IngredientPageOutputMapper;
import cmahy.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.webapp.taco.shop.kernel.domain.page.IngredientPage;
import cmahy.webapp.taco.shop.kernel.exception.RequiredException;
import cmahy.webapp.taco.shop.kernel.vo.output.IngredientOutputVo;
import cmahy.webapp.taco.shop.kernel.vo.output.IngredientPageOutputVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngredientPageOutputMapperTest {

    @Mock
    private IngredientOutputMapper ingredientOutputMapper;

    @InjectMocks
    private IngredientPageOutputMapper ingredientPageOutputMapper;

    @Test
    void map() {
        assertDoesNotThrow(() -> {
            int ingredientsSize = Generator.randomInt(50, 1000);

            Collection<IngredientOutputVo> expectedIngredientsOutput = new ArrayList<>(ingredientsSize);

            IngredientPage<Ingredient> ingredientPage = new IngredientPage<>(
                Stream.generate(() -> {
                    Ingredient ingredient = mock(Ingredient.class);

                    IngredientOutputVo ingredientOutputVo = mock(IngredientOutputVo.class);
                    expectedIngredientsOutput.add(ingredientOutputVo);

                    try {
                        when(ingredientOutputMapper.map(ingredient)).thenReturn(ingredientOutputVo);
                    } catch (RequiredException ignored) {}

                    return ingredient;
                })
                    .limit(ingredientsSize)
                    .toList(),
                Generator.randomLongEqualOrAboveZero()
            );

            IngredientPageOutputVo actual = ingredientPageOutputMapper.map(ingredientPage);

            assertThat(actual).isNotNull();

            assertThat(actual.content()).containsExactlyInAnyOrderElementsOf(expectedIngredientsOutput);
            assertThat(actual.totalElements()).isEqualTo(ingredientPage.totalElements());
        });
    }

    @Test
    void map_whenGivenValueIsNull_thenThrowNullAssertion() {
        assertThrows(RequiredException.class, () -> {
            ingredientPageOutputMapper.map(null);
        });
    }
}
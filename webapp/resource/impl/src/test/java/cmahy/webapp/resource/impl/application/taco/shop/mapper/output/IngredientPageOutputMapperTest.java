package cmahy.webapp.resource.impl.application.taco.shop.mapper.output;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.IngredientOutputAppVo;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.IngredientPageOutputAppVo;
import cmahy.webapp.resource.impl.domain.taco.Ingredient;
import cmahy.webapp.resource.impl.domain.taco.page.IngredientPage;
import cmahy.webapp.resource.impl.exception.NullException;
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

            Collection<IngredientOutputAppVo> expectedIngredientsOutput = new ArrayList<>(ingredientsSize);

            IngredientPage ingredientPage = new IngredientPage(
                Stream.generate(() -> {
                    Ingredient ingredient = mock(Ingredient.class);

                    IngredientOutputAppVo ingredientOutputAppVo = mock(IngredientOutputAppVo.class);
                    expectedIngredientsOutput.add(ingredientOutputAppVo);

                    when(ingredientOutputMapper.map(ingredient)).thenReturn(ingredientOutputAppVo);

                    return ingredient;
                })
                    .limit(ingredientsSize)
                    .toList(),
                Generator.randomLongEqualOrAboveZero()
            );

            IngredientPageOutputAppVo actual = ingredientPageOutputMapper.map(ingredientPage);

            assertThat(actual).isNotNull();

            assertThat(actual.content()).containsExactlyInAnyOrderElementsOf(expectedIngredientsOutput);
            assertThat(actual.totalElements()).isEqualTo(ingredientPage.totalElements());
        });
    }

    @Test
    void map_whenGivenValueIsNull_thenThrowNullAssertion() {
        assertThrows(NullException.class, () -> {
            ingredientPageOutputMapper.map(null);
        });
    }
}
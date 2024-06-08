package cmahy.webapp.resource.impl.adapter.api.taco.shop.mapper.output;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.api.taco.shop.vo.output.IngredientOutputApiVo;
import cmahy.webapp.resource.api.taco.shop.vo.output.IngredientPageOutputApiVo;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.IngredientOutputAppVo;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.IngredientPageOutputAppVo;
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
class IngredientPageOutputAppMapperTest {

    @Mock
    private IngredientOutputAppMapper ingredientOutputAppMapper;

    @InjectMocks
    private IngredientPageOutputAppMapper ingredientPageOutputAppMapper;

    @Test
    void map() {
        assertDoesNotThrow(() -> {
            int ingredientsSize = Generator.randomInt(50, 1000);

            Collection<IngredientOutputApiVo> expectedIngredientsOutput = new ArrayList<>(ingredientsSize);

            IngredientPageOutputAppVo ingredientPage = new IngredientPageOutputAppVo(
                Stream.generate(() -> {
                        IngredientOutputAppVo ingredientOutputAppVo = mock(IngredientOutputAppVo.class);

                        IngredientOutputApiVo ingredientOutputApiVo = mock(IngredientOutputApiVo.class);
                        expectedIngredientsOutput.add(ingredientOutputApiVo);

                        when(ingredientOutputAppMapper.map(ingredientOutputAppVo)).thenReturn(ingredientOutputApiVo);

                        return ingredientOutputAppVo;
                    })
                    .limit(ingredientsSize)
                    .toList(),
                Generator.randomLongEqualOrAboveZero()
            );

            IngredientPageOutputApiVo actual = ingredientPageOutputAppMapper.map(ingredientPage);

            assertThat(actual).isNotNull();

            assertThat(actual.content()).containsExactlyInAnyOrderElementsOf(expectedIngredientsOutput);
            assertThat(actual.totalElements()).isEqualTo(ingredientPage.totalElements());
        });
    }

    @Test
    void map_whenGivenValueIsNull_thenThrowNullAssertion() {
        assertThrows(NullException.class, () -> {
            ingredientPageOutputAppMapper.map(null);
        });
    }
}
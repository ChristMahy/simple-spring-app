package cmahy.webapp.resource.impl.application.taco.shop.mapper.output;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.domain.taco.Ingredient;
import cmahy.webapp.resource.impl.domain.taco.Taco;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.taco.shop.vo.output.IngredientOutputVo;
import cmahy.webapp.resource.taco.shop.vo.output.TacoOutputVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static cmahy.common.helper.Generator.generateAString;
import static cmahy.common.helper.Generator.randomLongEqualOrAboveZero;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TacoOutputMapperTest {

    @Mock
    private IngredientOutputMapper ingredientOutputMapper;

    @InjectMocks
    private TacoOutputMapper tacoOutputMapper;

    @Test
    void map() {
        assertDoesNotThrow(() -> {
            List<Ingredient> ingredients = Stream.generate(() -> mock(Ingredient.class))
                .limit(Generator.randomInt(10, 100))
                .toList();
            List<IngredientOutputVo> ingredientOutputVos = ingredients.stream()
                .map(ingredient -> {
                    IngredientOutputVo output = mock(IngredientOutputVo.class);

                    when(ingredientOutputMapper.map(ingredient)).thenReturn(output);

                    return output;
                })
                .toList();
            Taco taco = new Taco();

            taco.setId(randomLongEqualOrAboveZero());
            taco.setName(generateAString());
            taco.setCreatedAt(new Date());
            taco.setIngredients(ingredients);

            TacoOutputVo actual = tacoOutputMapper.map(taco);

            assertThat(actual).isNotNull();
            assertThat(actual.id()).isNotNull();
            assertThat(actual.id().value()).isEqualTo(taco.getId());
            assertThat(actual.createdAt()).isEqualTo(taco.getCreatedAt());
            assertThat(actual.ingredients()).containsExactlyElementsOf(ingredientOutputVos);
            assertThat(actual.name()).isEqualTo(taco.getName());
        });
    }

    @Test
    void map_whenGivenValueIsNull_thenThrowNullAssertion() {
        assertThrows(NullException.class, () -> {
            tacoOutputMapper.map(null);
        });
    }
}
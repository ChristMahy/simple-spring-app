package cmahy.simple.spring.webapp.taco.shop.kernel.application.mapper.output;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.mapper.output.IngredientOutputMapper;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.mapper.output.TacoOutputMapper;
import cmahy.simple.spring.webapp.taco.shop.kernel.exception.RequiredException;
import cmahy.simple.spring.webapp.taco.shop.kernel.vo.output.IngredientOutputVo;
import cmahy.simple.spring.webapp.taco.shop.kernel.vo.output.TacoOutputVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static cmahy.simple.spring.common.helper.Generator.generateAString;
import static cmahy.simple.spring.common.helper.Generator.randomUUID;
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
            List<IngredientStub> ingredients = Stream.generate(() -> mock(IngredientStub.class))
                .limit(Generator.randomInt(10, 100))
                .toList();
            List<IngredientOutputVo> ingredientOutputVos = ingredients.stream()
                .map(ingredient -> {
                    IngredientOutputVo output = mock(IngredientOutputVo.class);

                    try {
                        when(ingredientOutputMapper.map(ingredient)).thenReturn(output);
                    } catch (RequiredException ignored) {}

                    return output;
                })
                .toList();
            Taco taco = new TacoStub()
                .setId(randomUUID())
                .setName(generateAString())
                .setCreatedAt(new Date())
                .setIngredients(ingredients);

            TacoOutputVo actual = tacoOutputMapper.map(taco);

            assertThat(actual)
                .isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("id", "ingredients")
                .isEqualTo(taco);

            assertThat(actual.id().value()).isEqualTo(taco.getId());
            assertThat(actual.ingredients()).containsExactlyElementsOf(ingredientOutputVos);
        });
    }

    @Test
    void map_whenGivenValueIsNull_thenThrowNullAssertion() {
        assertThrows(RequiredException.class, () -> {
            tacoOutputMapper.map(null);
        });
    }
}
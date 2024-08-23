package cmahy.webapp.kernel.taco.shop.impl.application.mapper.output;

import cmahy.webapp.taco.shop.kernel.application.mapper.output.IngredientOutputMapper;
import cmahy.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.webapp.taco.shop.kernel.domain.IngredientType;
import cmahy.webapp.taco.shop.kernel.exception.RequiredException;
import cmahy.webapp.taco.shop.kernel.vo.output.IngredientOutputVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static cmahy.common.helper.Generator.generateAString;
import static cmahy.common.helper.Generator.randomEnum;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class IngredientOutputMapperTest {

    @InjectMocks
    private IngredientOutputMapper ingredientOutputMapper;

    @Test
    void map() {
        assertDoesNotThrow(() -> {
            Ingredient ingredient = new Ingredient();

            ingredient.setId(generateAString());
            ingredient.setName(generateAString());
            ingredient.setType(randomEnum(IngredientType.class));

            IngredientOutputVo actual = ingredientOutputMapper.map(ingredient);

            assertThat(actual).isNotNull();
            assertThat(actual.id()).isNotNull();
            assertThat(actual.id().value()).isEqualTo(ingredient.getId());
            assertThat(actual.name()).isEqualTo(ingredient.getName());
            assertThat(actual.type()).isEqualTo(ingredient.getType().name());
        });
    }

    @Test
    void map_whenGivenValueIsNull_thenThrowNullAssertion() {
        assertThrows(RequiredException.class, () -> {
            ingredientOutputMapper.map(null);
        });
    }
}
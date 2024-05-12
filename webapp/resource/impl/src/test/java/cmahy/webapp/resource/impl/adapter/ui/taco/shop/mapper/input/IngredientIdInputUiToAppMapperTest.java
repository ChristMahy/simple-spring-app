package cmahy.webapp.resource.impl.adapter.ui.taco.shop.mapper.input;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.domain.taco.id.IngredientId;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.ui.taco.vo.id.IngredientUiId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class IngredientIdInputUiToAppMapperTest {

    @InjectMocks
    private IngredientIdInputUiToAppMapper ingredientIdInputUiToAppMapper;

    @Test
    void map() {
        assertDoesNotThrow(() -> {
            IngredientUiId ingredientUiId = new IngredientUiId(
                Generator.generateAString()
            );

            IngredientId actual = ingredientIdInputUiToAppMapper.map(
                ingredientUiId
            );

            assertThat(actual).isNotNull();
            assertThat(actual.value()).isEqualTo(ingredientUiId.value());
        });
    }

    @Test
    void map_whenGivenValueIsNull_thenThrowNullAssertion() {
        assertThrows(NullException.class, () -> {
            ingredientIdInputUiToAppMapper.map(null);
        });
    }
}
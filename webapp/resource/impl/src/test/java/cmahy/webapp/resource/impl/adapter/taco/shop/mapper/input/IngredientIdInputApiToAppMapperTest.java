package cmahy.webapp.resource.impl.adapter.taco.shop.mapper.input;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.domain.taco.id.IngredientId;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.ui.taco.vo.id.IngredientApiId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class IngredientIdInputApiToAppMapperTest {

    @InjectMocks
    private IngredientIdInputApiToAppMapper ingredientIdInputApiToAppMapper;

    @Test
    void map() {
        assertDoesNotThrow(() -> {
            IngredientApiId ingredientApiId = new IngredientApiId(
                Generator.generateAString()
            );

            IngredientId actual = ingredientIdInputApiToAppMapper.map(
                ingredientApiId
            );

            assertThat(actual).isNotNull();
            assertThat(actual.value()).isEqualTo(ingredientApiId.value());
        });
    }

    @Test
    void map_whenGivenValueIsNull_thenThrowNullAssertion() {
        assertThrows(NullException.class, () -> {
            ingredientIdInputApiToAppMapper.map(null);
        });
    }
}
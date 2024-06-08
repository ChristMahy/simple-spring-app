package cmahy.webapp.resource.impl.adapter.api.taco.shop.mapper.input;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.api.taco.shop.vo.id.IngredientApiId;
import cmahy.webapp.resource.impl.domain.taco.id.IngredientId;
import cmahy.webapp.resource.impl.exception.NullException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class IngredientApiIdMapperTest {

    @InjectMocks
    private IngredientApiIdMapper ingredientApiIdMapper;

    @Test
    void map() {
        assertDoesNotThrow(() -> {
            IngredientApiId ingredientApiId = new IngredientApiId(Generator.generateAString());

            IngredientId actual = ingredientApiIdMapper.map(ingredientApiId);

            assertThat(actual)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(ingredientApiId);
        });
    }

    @Test
    void map_whenGivenValueIsNull_thenThrowNullAssertion() {
        assertThrows(NullException.class, () -> {
            ingredientApiIdMapper.map(null);
        });
    }
}
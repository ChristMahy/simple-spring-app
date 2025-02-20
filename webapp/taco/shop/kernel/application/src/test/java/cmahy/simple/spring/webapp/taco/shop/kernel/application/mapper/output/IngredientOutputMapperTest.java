package cmahy.simple.spring.webapp.taco.shop.kernel.application.mapper.output;

import cmahy.simple.spring.common.entity.id.EntityId;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.mapper.output.IngredientOutputMapper;
import cmahy.simple.spring.webapp.taco.shop.kernel.exception.RequiredException;
import cmahy.simple.spring.webapp.taco.shop.kernel.vo.output.IngredientOutputVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.function.BiPredicate;

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
            Ingredient ingredient = new IngredientStub()
                .setId(randomUUID())
                .setName(generateAString())
                .setType(randomEnum(IngredientType.class));

            IngredientOutputVo actual = ingredientOutputMapper.map(ingredient);

            BiPredicate<EntityId<?>, ?> idPredicate = (entityId, id) -> (Objects.isNull(entityId) && Objects.isNull(id)) || (Objects.nonNull(entityId) && entityId.value().equals(id));

            assertThat(actual)
                .isNotNull()
                .usingRecursiveComparison()
                .withEqualsForFields(idPredicate, "id")
                .withEnumStringComparison()
                .isEqualTo(ingredient);
        });
    }

    @Test
    void map_whenGivenValueIsNull_thenThrowNullAssertion() {
        assertThrows(RequiredException.class, () -> {
            ingredientOutputMapper.map(null);
        });
    }
}
package cmahy.webapp.resource.impl.application.taco.shop.query;

import cmahy.webapp.resource.impl.application.taco.shop.mapper.output.IngredientOutputMapper;
import cmahy.webapp.resource.impl.application.taco.shop.repository.IngredientRepository;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.IngredientOutputAppVo;
import cmahy.webapp.resource.impl.domain.taco.Ingredient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static cmahy.common.helper.Generator.randomInt;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetIngredientByTypeQueryTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private IngredientOutputMapper ingredientOutputMapper;

    @InjectMocks
    private GetIngredientByTypeQuery getIngredientByTypeQuery;

    @Test
    void execute() {
        assertDoesNotThrow(() -> {
            Set<Ingredient> ingredients = Stream.generate(() -> mock(Ingredient.class))
                .limit(randomInt(20, 100))
                .collect(Collectors.toSet());
            Set<IngredientOutputAppVo> ingredientOutputAppVos = ingredients.stream()
                .map(ingredient -> {
                    IngredientOutputAppVo ingredientOutputAppVo = mock(IngredientOutputAppVo.class);

                    when(ingredientOutputMapper.map(ingredient)).thenReturn(ingredientOutputAppVo);

                    return ingredientOutputAppVo;
                })
                .collect(Collectors.toSet());

            Ingredient.Type type = mock(Ingredient.Type.class);

            when(ingredientRepository.findByType(type)).thenReturn(ingredients);

            Set<IngredientOutputAppVo> actual = getIngredientByTypeQuery.execute(type);

            assertThat(actual).isNotNull();
            assertThat(actual).containsExactlyElementsOf(ingredientOutputAppVos);
        });
    }
}
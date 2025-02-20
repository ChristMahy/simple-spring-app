package cmahy.simple.spring.webapp.taco.shop.kernel.application.query;

import cmahy.simple.spring.webapp.taco.shop.kernel.application.mapper.output.IngredientOutputMapper;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.repository.IngredientRepository;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.IngredientType;
import cmahy.simple.spring.webapp.taco.shop.kernel.exception.RequiredException;
import cmahy.simple.spring.webapp.taco.shop.kernel.vo.output.IngredientOutputVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static cmahy.simple.spring.common.helper.Generator.randomInt;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetIngredientByTypeQueryTest {

    @Mock
    private IngredientRepository<Ingredient> ingredientRepository;

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
            Set<IngredientOutputVo> ingredientOutputVos = ingredients.stream()
                .map(ingredient -> {
                    IngredientOutputVo ingredientOutputVo = mock(IngredientOutputVo.class);

                    try {
                        when(ingredientOutputMapper.map(ingredient)).thenReturn(ingredientOutputVo);
                    } catch (RequiredException ignored) {}

                    return ingredientOutputVo;
                })
                .collect(Collectors.toSet());

            IngredientType type = mock(IngredientType.class);

            when(ingredientRepository.findByType(type)).thenReturn(ingredients);

            Set<IngredientOutputVo> actual = getIngredientByTypeQuery.execute(type);

            assertThat(actual).isNotNull();
            assertThat(actual).containsExactlyElementsOf(ingredientOutputVos);
        });
    }
}
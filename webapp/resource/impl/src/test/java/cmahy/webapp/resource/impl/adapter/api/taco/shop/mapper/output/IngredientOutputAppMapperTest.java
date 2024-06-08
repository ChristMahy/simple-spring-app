package cmahy.webapp.resource.impl.adapter.api.taco.shop.mapper.output;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.api.taco.shop.vo.id.IngredientApiId;
import cmahy.webapp.resource.api.taco.shop.vo.output.IngredientOutputApiVo;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.IngredientOutputAppVo;
import cmahy.webapp.resource.impl.domain.taco.id.IngredientId;
import cmahy.webapp.resource.impl.exception.NullException;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngredientOutputAppMapperTest {

    @Mock
    private IngredientIdMapper ingredientIdMapper;

    @InjectMocks
    private IngredientOutputAppMapper ingredientOutputAppMapper;

    @Test
    void map() {
        assertDoesNotThrow(() -> {
            IngredientId id = mock(IngredientId.class);
            IngredientApiId apiId = mock(IngredientApiId.class);

            when(ingredientIdMapper.map(id)).thenReturn(apiId);

            IngredientOutputAppVo ingredientOutputAppVo = new IngredientOutputAppVo(
                id,
                Generator.generateAString(),
                Generator.generateAString()
            );

            IngredientOutputApiVo actual = ingredientOutputAppMapper.map(ingredientOutputAppVo);

            assertThat(actual)
                .isNotNull()
                .usingRecursiveComparison(
                    RecursiveComparisonConfiguration.builder()
                        .withIgnoredFields("id")
                        .build()
                )
                .isEqualTo(ingredientOutputAppVo);

            assertThat(actual.id()).isEqualTo(apiId);
        });
    }

    @Test
    void map_whenGivenValueIsNull_thenThrowNullAssertion() {
        assertThrows(NullException.class, () -> {
            ingredientOutputAppMapper.map(null);
        });
    }
}
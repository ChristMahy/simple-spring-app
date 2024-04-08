package cmahy.webapp.resource.impl.adapter.ui.taco.shop.mapper;

import cmahy.webapp.resource.impl.application.taco.shop.vo.output.IngredientOutputAppVo;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.TacoOutputAppVo;
import cmahy.webapp.resource.impl.domain.taco.id.TacoId;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.ui.taco.vo.output.IngredientOutputApiVo;
import cmahy.webapp.resource.ui.taco.vo.output.TacoOutputApiVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static cmahy.common.helper.Generator.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TacoOutputApiMapperTest {

    @Mock
    private IngredientOutputApiMapper ingredientOutputApiMapper;

    @InjectMocks
    private TacoOutputApiMapper tacoOutputApiMapper;

    @Test
    void map() {
        assertDoesNotThrow(() -> {
            TacoOutputAppVo outputAppVo = new TacoOutputAppVo(
                new TacoId(randomLongEqualOrAboveZero()),
                new Date(),
                generateAString(),
                Stream.generate(() -> mock(IngredientOutputAppVo.class))
                    .limit(randomInt(10, 50))
                    .toList()
            );

            List<IngredientOutputApiVo> ingredientOutputApiVos = outputAppVo.ingredients().stream()
                .map(ingredientOutputAppVo -> {
                    IngredientOutputApiVo ingredientOutputApiVo = mock(IngredientOutputApiVo.class);

                    when(ingredientOutputApiMapper.map(ingredientOutputAppVo)).thenReturn(ingredientOutputApiVo);

                    return ingredientOutputApiVo;
                })
                .toList();

            TacoOutputApiVo actual = tacoOutputApiMapper.map(outputAppVo);

            assertThat(actual).isNotNull();
            assertThat(actual.id()).isNotNull();
            assertThat(actual.id().value()).isEqualTo(outputAppVo.id().value());
            assertThat(actual.createdAt()).isEqualTo(outputAppVo.createdAt());
            assertThat(actual.name()).isEqualTo(outputAppVo.name());
            assertThat(actual.ingredients()).containsExactlyElementsOf(ingredientOutputApiVos);
        });
    }

    @Test
    void map_givenIdIsNull_thenIdShouldBeNull() {
        assertDoesNotThrow(() -> {
            TacoOutputAppVo outputAppVo = new TacoOutputAppVo(
                null,
                new Date(),
                generateAString(),
                Stream.generate(() -> {
                    IngredientOutputAppVo ingredientOutputAppVo = mock(IngredientOutputAppVo.class);

                    when(ingredientOutputApiMapper.map(ingredientOutputAppVo)).thenAnswer(ignored -> mock(IngredientOutputApiVo.class));

                    return ingredientOutputAppVo;
                })
                    .limit(randomInt(10, 50))
                    .toList()
            );

            TacoOutputApiVo actual = tacoOutputApiMapper.map(outputAppVo);

            assertThat(actual).isNotNull();
            assertThat(actual.id()).isNull();
        });
    }

    @Test
    void map_valueIsNull_thenThrowNullAssertion() {
        assertThrows(NullException.class, () -> {
            tacoOutputApiMapper.map(null);
        });
    }
}
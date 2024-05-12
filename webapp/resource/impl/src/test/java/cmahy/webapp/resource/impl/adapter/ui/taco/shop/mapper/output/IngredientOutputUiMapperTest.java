package cmahy.webapp.resource.impl.adapter.ui.taco.shop.mapper.output;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.IngredientOutputAppVo;
import cmahy.webapp.resource.impl.domain.taco.id.IngredientId;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.ui.taco.vo.output.IngredientOutputUiVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class IngredientOutputUiMapperTest {

    @InjectMocks
    private IngredientOutputUiMapper ingredientOutputUiMapper;

    @Test
    void map() {
        assertDoesNotThrow(() -> {
            IngredientOutputAppVo appOutputVo = new IngredientOutputAppVo(
                new IngredientId(Generator.generateAString()),
                Generator.generateAString(),
                Generator.generateAString()
            );

            IngredientOutputUiVo actual = ingredientOutputUiMapper.map(appOutputVo);

            assertThat(actual).isNotNull();
            assertThat(actual.id()).isNotNull();
            assertThat(actual.id().value()).isEqualTo(appOutputVo.id().value());
            assertThat(actual.name()).isEqualTo(appOutputVo.name());
            assertThat(actual.type()).isEqualTo(appOutputVo.type());
        });
    }

    @Test
    void map_givenIdIsNull_thenIdShouldBeNull() {
        assertDoesNotThrow(() -> {
            IngredientOutputAppVo appOutputVo = new IngredientOutputAppVo(
                null,
                Generator.generateAString(),
                Generator.generateAString()
            );

            IngredientOutputUiVo actual = ingredientOutputUiMapper.map(appOutputVo);

            assertThat(actual).isNotNull();
            assertThat(actual.id()).isNull();
        });
    }

    @Test
    void map_valueIsNull_thenThrowNullAssertion() {
        assertThrows(NullException.class, () -> {
            ingredientOutputUiMapper.map(null);
        });
    }
}
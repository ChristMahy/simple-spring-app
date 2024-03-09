package cmahy.webapp.resource.impl.adapter.ui.taco.shop.mapper;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.IngredientOutputAppVo;
import cmahy.webapp.resource.impl.domain.taco.id.IngredientId;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.ui.taco.vo.output.IngredientOutputApiVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class IngredientOutputApiMapperTest {

    @InjectMocks
    private IngredientOutputApiMapper ingredientOutputApiMapper;

    @Test
    void map() {
        assertDoesNotThrow(() -> {
            IngredientOutputAppVo appOutputVo = new IngredientOutputAppVo(
                new IngredientId(Generator.generateAString()),
                Generator.generateAString(),
                Generator.generateAString()
            );

            IngredientOutputApiVo actual = ingredientOutputApiMapper.map(appOutputVo);

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

            IngredientOutputApiVo actual = ingredientOutputApiMapper.map(appOutputVo);

            assertThat(actual).isNotNull();
            assertThat(actual.id()).isNull();
            assertThat(actual.name()).isEqualTo(appOutputVo.name());
            assertThat(actual.type()).isEqualTo(appOutputVo.type());
        });
    }

    @Test
    void map_valueIsNull_thenThrowNullAssertion() {
        assertThrows(NullException.class, () -> {
            ingredientOutputApiMapper.map(null);
        });
    }
}
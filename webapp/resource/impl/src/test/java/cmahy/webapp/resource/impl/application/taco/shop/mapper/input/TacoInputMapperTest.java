package cmahy.webapp.resource.impl.application.taco.shop.mapper.input;

import cmahy.webapp.resource.impl.domain.taco.Taco;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.taco.shop.id.IngredientId;
import cmahy.webapp.resource.taco.shop.vo.input.TacoInputVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static cmahy.common.helper.Generator.generateAString;
import static cmahy.common.helper.Generator.randomInt;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class TacoInputMapperTest {

    @InjectMocks
    private TacoInputMapper tacoInputMapper;

    @Test
    void map() {
        assertDoesNotThrow(() -> {
            TacoInputVo tacoInputVo = new TacoInputVo(
                generateAString(),
                Stream.generate(() -> mock(IngredientId.class))
                    .limit(randomInt(10, 50))
                    .collect(Collectors.toSet())
            );

            Taco actual = tacoInputMapper.map(tacoInputVo);

            assertThat(actual).isNotNull();

            assertThat(actual.getName()).isEqualTo(tacoInputVo.name());

            assertThat(actual.getId()).isNull();
            assertThat(actual.getCreatedAt()).isNull();
            assertThat(actual.getIngredients()).isNullOrEmpty();
        });
    }

    @Test
    void map_whenGivenValueIsNull_thenThrowNullAssertion() {
        assertThrows(NullException.class, () -> {
            tacoInputMapper.map(null);
        });
    }
}
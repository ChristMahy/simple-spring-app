package cmahy.webapp.resource.impl.application.taco.shop.mapper.input;

import cmahy.webapp.resource.impl.application.taco.shop.vo.input.TacoInputAppVo;
import cmahy.webapp.resource.impl.domain.taco.Taco;
import cmahy.webapp.resource.impl.domain.taco.id.IngredientId;
import cmahy.webapp.resource.impl.exception.NullException;
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
class TacoInputAppMapperTest {

    @InjectMocks
    private TacoInputAppMapper tacoInputAppMapper;

    @Test
    void map() {
        assertDoesNotThrow(() -> {
            TacoInputAppVo tacoInputAppVo = new TacoInputAppVo(
                generateAString(),
                Stream.generate(() -> mock(IngredientId.class))
                    .limit(randomInt(10, 50))
                    .collect(Collectors.toSet())
            );

            Taco actual = tacoInputAppMapper.map(tacoInputAppVo);

            assertThat(actual).isNotNull();

            assertThat(actual.getName()).isEqualTo(tacoInputAppVo.name());

            assertThat(actual.getId()).isNull();
            assertThat(actual.getCreatedAt()).isNull();
            assertThat(actual.getIngredients()).isNullOrEmpty();
        });
    }

    @Test
    void map_whenGivenValueIsNull_thenThrowNullAssertion() {
        assertThrows(NullException.class, () -> {
            tacoInputAppMapper.map(null);
        });
    }
}
package cmahy.webapp.kernel.taco.shop.impl.application.mapper.input;

import cmahy.webapp.taco.shop.kernel.application.mapper.input.TacoInputMapper;
import cmahy.webapp.taco.shop.kernel.domain.Taco;
import cmahy.webapp.taco.shop.kernel.domain.builder.TacoBuilder;
import cmahy.webapp.taco.shop.kernel.domain.builder.TacoBuilderStub;
import cmahy.webapp.taco.shop.kernel.domain.builder.factory.TacoBuilderFactory;
import cmahy.webapp.taco.shop.kernel.domain.id.IngredientId;
import cmahy.webapp.taco.shop.kernel.exception.RequiredException;
import cmahy.webapp.taco.shop.kernel.vo.input.TacoInputVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static cmahy.common.helper.Generator.generateAString;
import static cmahy.common.helper.Generator.randomInt;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TacoInputMapperTest {

    @Mock
    private TacoBuilderFactory<Taco> tacoBuilderFactory;

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

            when(tacoBuilderFactory.create()).thenAnswer(_ -> new TacoBuilderStub());

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
        assertThrows(RequiredException.class, () -> {
            tacoInputMapper.map(null);
        });
    }
}
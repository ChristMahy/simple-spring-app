package cmahy.webapp.resource.impl.adapter.taco.shop.mapper.input;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.application.taco.shop.vo.input.TacoInputAppVo;
import cmahy.webapp.resource.impl.domain.taco.id.IngredientId;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.ui.taco.vo.id.IngredientApiId;
import cmahy.webapp.resource.ui.taco.vo.input.TacoInputApiVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TacoInputApiToAppMapperTest {

    @Mock
    private IngredientIdInputApiToAppMapper ingredientApiMapper;

    @InjectMocks
    private TacoInputApiToAppMapper tacoInputApiToAppMapper;

    @Test
    void map() {
        assertDoesNotThrow(() -> {
            List<IngredientApiId> ids = Stream.generate(() -> {
                var id = new IngredientApiId(Generator.generateAString(10));
                when(ingredientApiMapper.map(id)).thenAnswer(invocationOnMock -> new IngredientId(
                    invocationOnMock.getArgument(0, IngredientApiId.class).value()
                ));
                return id;
            })
                .limit(Generator.randomInt(20, 100))
                .toList();

            TacoInputApiVo taco = new TacoInputApiVo(
                Generator.generateAString(),
                ids
            );

            TacoInputAppVo actual = tacoInputApiToAppMapper.map(taco);

            assertThat(actual).isNotNull();
            assertThat(actual.name()).isEqualTo(taco.name());
            assertThat(actual.ingredientIds().stream().map(IngredientId::value).toList())
                .containsAll(ids.stream().map(IngredientApiId::value).toList());
        });
    }

    @Test
    void map_whenGivenValueIsNull_thenThrowNullAssertion() {
        assertThrows(NullException.class, () -> {
            tacoInputApiToAppMapper.map(null);
        });
    }
}
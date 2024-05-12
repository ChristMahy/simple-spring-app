package cmahy.webapp.resource.impl.adapter.ui.taco.shop.mapper.input;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.application.taco.shop.vo.input.TacoInputAppVo;
import cmahy.webapp.resource.impl.domain.taco.id.IngredientId;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.ui.taco.vo.id.IngredientUiId;
import cmahy.webapp.resource.ui.taco.vo.input.TacoInputUiVo;
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
class TacoInputUiToAppMapperTest {

    @Mock
    private IngredientIdInputUiToAppMapper ingredientApiMapper;

    @InjectMocks
    private TacoInputUiToAppMapper tacoInputUiToAppMapper;

    @Test
    void map() {
        assertDoesNotThrow(() -> {
            List<IngredientUiId> ids = Stream.generate(() -> {
                var id = new IngredientUiId(Generator.generateAString(10));
                when(ingredientApiMapper.map(id)).thenAnswer(invocationOnMock -> new IngredientId(
                    invocationOnMock.getArgument(0, IngredientUiId.class).value()
                ));
                return id;
            })
                .limit(Generator.randomInt(20, 100))
                .toList();

            TacoInputUiVo taco = new TacoInputUiVo(
                Generator.generateAString(),
                ids
            );

            TacoInputAppVo actual = tacoInputUiToAppMapper.map(taco);

            assertThat(actual).isNotNull();
            assertThat(actual.name()).isEqualTo(taco.name());
            assertThat(actual.ingredientIds().stream().map(IngredientId::value).toList())
                .containsAll(ids.stream().map(IngredientUiId::value).toList());
        });
    }

    @Test
    void map_whenGivenValueIsNull_thenThrowNullAssertion() {
        assertThrows(NullException.class, () -> {
            tacoInputUiToAppMapper.map(null);
        });
    }
}
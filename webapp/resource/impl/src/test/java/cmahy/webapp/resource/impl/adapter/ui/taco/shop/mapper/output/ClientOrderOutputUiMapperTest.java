package cmahy.webapp.resource.impl.adapter.ui.taco.shop.mapper.output;

import cmahy.webapp.resource.impl.application.taco.shop.vo.output.ClientOrderOutputAppVo;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.TacoOutputAppVo;
import cmahy.webapp.resource.impl.domain.taco.id.ClientOrderId;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.ui.taco.vo.output.ClientOrderOutputUiVo;
import cmahy.webapp.resource.ui.taco.vo.output.TacoOutputUiVo;
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
class ClientOrderOutputUiMapperTest {

    @Mock
    private TacoOutputUiMapper tacoOutputUiMapper;

    @InjectMocks
    private ClientOrderOutputUiMapper clientOrderOutputUiMapper;

    @Test
    void map() {
        assertDoesNotThrow(() -> {
            ClientOrderOutputAppVo clientOrderOutputAppVo = new ClientOrderOutputAppVo(
                new ClientOrderId(randomLongEqualOrAboveZero()),
                new Date(),
                generateAString(),
                generateAString(),
                generateAString(),
                generateAString(),
                generateAString(),
                generateAString(),
                generateAString(),
                generateAString(),
                Stream.generate(() -> mock(TacoOutputAppVo.class))
                    .limit(randomInt(10, 50))
                    .toList()
            );

            List<TacoOutputUiVo> tacoOutputUiVos = clientOrderOutputAppVo.tacos().stream()
                .map(tacoOutputAppVo -> {
                    TacoOutputUiVo tacoOutputUiVo = mock(TacoOutputUiVo.class);

                    when(tacoOutputUiMapper.map(tacoOutputAppVo)).thenReturn(tacoOutputUiVo);

                    return tacoOutputUiVo;
                })
                .toList();

            ClientOrderOutputUiVo actual = clientOrderOutputUiMapper.map(clientOrderOutputAppVo);

            assertThat(actual).isNotNull();
            assertThat(actual.id()).isNotNull();
            assertThat(actual.id().value()).isEqualTo(clientOrderOutputAppVo.id().value());
            assertThat(actual.deliveryName()).isEqualTo(clientOrderOutputAppVo.deliveryName());
            assertThat(actual.deliveryState()).isEqualTo(clientOrderOutputAppVo.deliveryState());
            assertThat(actual.deliveryStreet()).isEqualTo(clientOrderOutputAppVo.deliveryStreet());
            assertThat(actual.deliveryCity()).isEqualTo(clientOrderOutputAppVo.deliveryCity());
            assertThat(actual.deliveryZip()).isEqualTo(clientOrderOutputAppVo.deliveryZip());
            assertThat(actual.ccNumber()).isEqualTo(clientOrderOutputAppVo.ccNumber());
            assertThat(actual.ccExpiration()).isEqualTo(clientOrderOutputAppVo.ccExpiration());
            assertThat(actual.ccCVV()).isEqualTo(clientOrderOutputAppVo.ccCVV());
            assertThat(actual.placedAt()).isEqualTo(clientOrderOutputAppVo.placedAt());
            assertThat(actual.tacos()).containsExactlyElementsOf(tacoOutputUiVos);
        });
    }

    @Test
    void map_givenIdIsNull_thenIdShouldBeNull() {
        assertDoesNotThrow(() -> {
            ClientOrderOutputAppVo clientOrderOutputAppVo = new ClientOrderOutputAppVo(
                null,
                new Date(),
                generateAString(),
                generateAString(),
                generateAString(),
                generateAString(),
                generateAString(),
                generateAString(),
                generateAString(),
                generateAString(),
                Stream.generate(() -> {
                    TacoOutputAppVo tacoOutputAppVo = mock(TacoOutputAppVo.class);

                    when(tacoOutputUiMapper.map(tacoOutputAppVo)).thenReturn(mock(TacoOutputUiVo.class));

                    return tacoOutputAppVo;
                })
                    .limit(randomInt(10, 50))
                    .toList()
            );

            ClientOrderOutputUiVo actual = clientOrderOutputUiMapper.map(clientOrderOutputAppVo);

            assertThat(actual).isNotNull();
            assertThat(actual.id()).isNull();
        });
    }

    @Test
    void map_valueIsNull_thenThrowNullAssertion() {
        assertThrows(NullException.class, () -> {
            clientOrderOutputUiMapper.map(null);
        });
    }
}
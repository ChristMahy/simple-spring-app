package cmahy.webapp.resource.impl.adapter.ui.taco.shop.mapper;

import cmahy.webapp.resource.impl.application.taco.shop.vo.output.ClientOrderOutputAppVo;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.TacoOutputAppVo;
import cmahy.webapp.resource.impl.domain.taco.id.ClientOrderId;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.ui.taco.vo.output.ClientOrderOutputApiVo;
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
class ClientOrderOutputApiMapperTest {

    @Mock
    private TacoOutputApiMapper tacoOutputApiMapper;

    @InjectMocks
    private ClientOrderOutputApiMapper clientOrderOutputApiMapper;

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

            List<TacoOutputApiVo> tacoOutputApiVos = clientOrderOutputAppVo.tacos().stream()
                .map(tacoOutputAppVo -> {
                    TacoOutputApiVo tacoOutputApiVo = mock(TacoOutputApiVo.class);

                    when(tacoOutputApiMapper.map(tacoOutputAppVo)).thenReturn(tacoOutputApiVo);

                    return tacoOutputApiVo;
                })
                .toList();

            ClientOrderOutputApiVo actual = clientOrderOutputApiMapper.map(clientOrderOutputAppVo);

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
            assertThat(actual.tacos()).containsExactlyElementsOf(tacoOutputApiVos);
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

                    when(tacoOutputApiMapper.map(tacoOutputAppVo)).thenReturn(mock(TacoOutputApiVo.class));

                    return tacoOutputAppVo;
                })
                    .limit(randomInt(10, 50))
                    .toList()
            );

            ClientOrderOutputApiVo actual = clientOrderOutputApiMapper.map(clientOrderOutputAppVo);

            assertThat(actual).isNotNull();
            assertThat(actual.id()).isNull();
        });
    }

    @Test
    void map_valueIsNull_thenThrowNullAssertion() {
        assertThrows(NullException.class, () -> {
            clientOrderOutputApiMapper.map(null);
        });
    }
}
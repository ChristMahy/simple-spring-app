package cmahy.webapp.resource.impl.application.taco.shop.mapper.output;

import cmahy.webapp.resource.impl.application.taco.shop.vo.output.ClientOrderOutputAppVo;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.TacoOutputAppVo;
import cmahy.webapp.resource.impl.domain.taco.ClientOrder;
import cmahy.webapp.resource.impl.domain.taco.Taco;
import cmahy.webapp.resource.impl.exception.NullException;
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
class ClientOrderOutputMapperTest {

    @Mock
    private TacoOutputMapper tacoOutputMapper;

    @InjectMocks
    private ClientOrderOutputMapper clientOrderOutputMapper;

    @Test
    void map() {
        assertDoesNotThrow(() -> {
            List<Taco> tacos = Stream.generate(() -> mock(Taco.class))
                .limit(randomInt(10, 100))
                .toList();
            List<TacoOutputAppVo> tacoOutputAppVos = tacos.stream()
                .map(taco -> {
                    TacoOutputAppVo outputAppVo = mock(TacoOutputAppVo.class);

                    when(tacoOutputMapper.map(taco)).thenReturn(outputAppVo);

                    return outputAppVo;
                })
                .toList();

            ClientOrder order = new ClientOrder();

            order.setId(randomLongEqualOrAboveZero());
            order.setPlacedAt(new Date());
            order.setDeliveryName(generateAString());
            order.setDeliveryStreet(generateAString());
            order.setDeliveryState(generateAString());
            order.setDeliveryCity(generateAString());
            order.setDeliveryZip(generateAString());
            order.setCcNumber(generateAString());
            order.setCcCVV(generateAString());
            order.setCcExpiration(generateAString());
            order.setTacos(tacos);

            ClientOrderOutputAppVo actual = clientOrderOutputMapper.map(order);

            assertThat(actual).isNotNull();
            assertThat(actual.id()).isNotNull();
            assertThat(actual.id().value()).isEqualTo(order.getId());
            assertThat(actual.placedAt()).isEqualTo(order.getPlacedAt());
            assertThat(actual.deliveryName()).isEqualTo(order.getDeliveryName());
            assertThat(actual.deliveryStreet()).isEqualTo(order.getDeliveryStreet());
            assertThat(actual.deliveryCity()).isEqualTo(order.getDeliveryCity());
            assertThat(actual.deliveryState()).isEqualTo(order.getDeliveryState());
            assertThat(actual.deliveryZip()).isEqualTo(order.getDeliveryZip());
            assertThat(actual.ccNumber()).isEqualTo(order.getCcNumber());
            assertThat(actual.ccExpiration()).isEqualTo(order.getCcExpiration());
            assertThat(actual.ccCVV()).isEqualTo(order.getCcCVV());
            assertThat(actual.tacos()).containsExactlyElementsOf(tacoOutputAppVos);
        });
    }

    @Test
    void map_whenGivenValueIsNull_thenThrowNullAssertion() {
        assertThrows(NullException.class, () -> {
            clientOrderOutputMapper.map(null);
        });
    }
}
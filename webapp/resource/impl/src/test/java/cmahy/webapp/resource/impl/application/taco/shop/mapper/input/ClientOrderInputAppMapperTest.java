package cmahy.webapp.resource.impl.application.taco.shop.mapper.input;

import cmahy.webapp.resource.impl.application.taco.shop.vo.input.ClientOrderInputAppVo;
import cmahy.webapp.resource.impl.application.taco.shop.vo.input.TacoInputAppVo;
import cmahy.webapp.resource.impl.domain.taco.ClientOrder;
import cmahy.webapp.resource.impl.exception.NullException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static cmahy.common.helper.Generator.generateAString;
import static cmahy.common.helper.Generator.randomInt;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ClientOrderInputAppMapperTest {

    @InjectMocks
    private ClientOrderInputAppMapper clientOrderInputAppMapper;

    @Test
    void map() {
        assertDoesNotThrow(() -> {
            ClientOrderInputAppVo inputAppVo = new ClientOrderInputAppVo(
                generateAString(),
                generateAString(),
                generateAString(),
                generateAString(),
                generateAString(),
                generateAString(),
                generateAString(),
                generateAString(),
                Stream.generate(() -> mock(TacoInputAppVo.class))
                    .limit(randomInt(10, 50))
                    .toList()
            );

            ClientOrder actual = clientOrderInputAppMapper.map(inputAppVo);

            assertThat(actual).isNotNull();
            assertThat(actual.getDeliveryName()).isEqualTo(inputAppVo.deliveryName());
            assertThat(actual.getDeliveryCity()).isEqualTo(inputAppVo.deliveryCity());
            assertThat(actual.getDeliveryState()).isEqualTo(inputAppVo.deliveryState());
            assertThat(actual.getDeliveryStreet()).isEqualTo(inputAppVo.deliveryStreet());
            assertThat(actual.getDeliveryZip()).isEqualTo(inputAppVo.deliveryZip());
            assertThat(actual.getCcCVV()).isEqualTo(inputAppVo.ccCVV());
            assertThat(actual.getCcExpiration()).isEqualTo(inputAppVo.ccExpiration());
            assertThat(actual.getCcNumber()).isEqualTo(inputAppVo.ccNumber());

            assertThat(actual.getId()).isNull();
            assertThat(actual.getPlacedAt()).isNull();
            assertThat(actual.getTacos()).isNullOrEmpty();
        });
    }

    @Test
    public void map_whenGivenValueIsNull_thenThrowNullAssertion() {
        assertThrows(NullException.class, () -> {
            clientOrderInputAppMapper.map(null);
        });
    }
}
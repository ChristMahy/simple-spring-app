package cmahy.webapp.resource.impl.application.taco.shop.mapper.input;

import cmahy.webapp.resource.impl.domain.taco.ClientOrder;
import cmahy.webapp.resource.impl.domain.user.User;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.taco.shop.vo.input.ClientOrderInputVo;
import cmahy.webapp.resource.taco.shop.vo.input.TacoInputVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static cmahy.common.helper.Generator.generateAString;
import static cmahy.common.helper.Generator.randomInt;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ClientOrderInputMapperTest {

    @InjectMocks
    private ClientOrderInputMapper clientOrderInputMapper;

    @Test
    void map() {
        assertDoesNotThrow(() -> {
            User user = mock(User.class);
            ClientOrderInputVo inputVo = new ClientOrderInputVo(
                generateAString(),
                generateAString(),
                generateAString(),
                generateAString(),
                generateAString(),
                generateAString(),
                generateAString(),
                generateAString(),
                Stream.generate(() -> mock(TacoInputVo.class))
                    .limit(randomInt(10, 50))
                    .toList()
            );

            ClientOrder actual = clientOrderInputMapper.map(inputVo, user);

            assertThat(actual).isNotNull();
            assertThat(actual.getDeliveryName()).isEqualTo(inputVo.deliveryName());
            assertThat(actual.getDeliveryCity()).isEqualTo(inputVo.deliveryCity());
            assertThat(actual.getDeliveryState()).isEqualTo(inputVo.deliveryState());
            assertThat(actual.getDeliveryStreet()).isEqualTo(inputVo.deliveryStreet());
            assertThat(actual.getDeliveryZip()).isEqualTo(inputVo.deliveryZip());
            assertThat(actual.getCcCVV()).isEqualTo(inputVo.ccCVV());
            assertThat(actual.getCcExpiration()).isEqualTo(inputVo.ccExpiration());
            assertThat(actual.getCcNumber()).isEqualTo(inputVo.ccNumber());

            assertThat(actual.getId()).isNull();
            assertThat(actual.getPlacedAt()).isNull();
            assertThat(actual.getTacos()).isNullOrEmpty();
        });
    }

    @Test
    public void map_whenGivenValueIsNull_thenThrowNullAssertion() {
        assertThrows(NullException.class, () -> {
            User user = mock(User.class);

            clientOrderInputMapper.map(null, user);
        });
    }
}
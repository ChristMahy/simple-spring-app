package cmahy.webapp.kernel.taco.shop.impl.application.mapper.input;

import cmahy.webapp.taco.shop.kernel.application.mapper.input.ClientOrderInputMapper;
import cmahy.webapp.taco.shop.kernel.domain.ClientOrder;
import cmahy.webapp.taco.shop.kernel.exception.RequiredException;
import cmahy.webapp.taco.shop.kernel.vo.input.ClientOrderInputVo;
import cmahy.webapp.taco.shop.kernel.vo.input.TacoInputVo;
import cmahy.webapp.user.kernel.domain.User;
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
        assertThrows(RequiredException.class, () -> {
            User user = mock(User.class);

            clientOrderInputMapper.map(null, user);
        });
    }
}
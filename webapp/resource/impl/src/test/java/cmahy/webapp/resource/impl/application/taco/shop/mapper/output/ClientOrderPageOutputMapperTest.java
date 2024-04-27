package cmahy.webapp.resource.impl.application.taco.shop.mapper.output;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.ClientOrderOutputAppVo;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.ClientOrderPageOutputAppVo;
import cmahy.webapp.resource.impl.domain.taco.ClientOrder;
import cmahy.webapp.resource.impl.domain.taco.page.ClientOrderPage;
import cmahy.webapp.resource.impl.exception.NullException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import static cmahy.common.helper.Generator.randomInt;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientOrderPageOutputMapperTest {

    @Mock
    private ClientOrderOutputMapper clientOrderOutputMapper;

    @InjectMocks
    private ClientOrderPageOutputMapper clientOrderPageOutputMapper;

    @Test
    void map() {
        assertDoesNotThrow(() -> {
            int clientOrdersSize = randomInt(50, 1000);

            Collection<ClientOrderOutputAppVo> expectedClientOrderOutputs = new ArrayList<>(clientOrdersSize);

            ClientOrderPage clientOrderPage = new ClientOrderPage(
                Stream.generate(() -> {
                        ClientOrder clientOrder = mock(ClientOrder.class);

                        ClientOrderOutputAppVo clientOrderOutputAppVo = mock(ClientOrderOutputAppVo.class);
                        expectedClientOrderOutputs.add(clientOrderOutputAppVo);

                        when(clientOrderOutputMapper.map(clientOrder)).thenReturn(clientOrderOutputAppVo);

                        return clientOrder;
                    })
                    .limit(clientOrdersSize)
                    .toList(),
                Generator.randomLongEqualOrAboveZero()
            );

            ClientOrderPageOutputAppVo actual = clientOrderPageOutputMapper.map(clientOrderPage);

            assertThat(actual).isNotNull();

            assertThat(actual.content()).containsExactlyInAnyOrderElementsOf(expectedClientOrderOutputs);
            assertThat(actual.totalElements()).isEqualTo(clientOrderPage.totalElements());
        });
    }

    @Test
    void map_whenGivenValueIsNull_thenThrowNullAssertion() {
        assertThrows(NullException.class, () -> {
            clientOrderPageOutputMapper.map(null);
        });
    }
}
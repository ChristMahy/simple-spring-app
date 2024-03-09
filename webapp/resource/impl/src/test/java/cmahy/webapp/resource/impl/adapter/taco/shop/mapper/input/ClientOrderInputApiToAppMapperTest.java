package cmahy.webapp.resource.impl.adapter.taco.shop.mapper.input;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.application.taco.shop.vo.input.ClientOrderInputAppVo;
import cmahy.webapp.resource.impl.application.taco.shop.vo.input.TacoInputAppVo;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.ui.taco.vo.input.ClientOrderInputApiVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ClientOrderInputApiToAppMapperTest {

    @InjectMocks
    private ClientOrderInputApiToAppMapper clientOrderInputApiToAppMapper;

    @Test
    void map() {
        assertDoesNotThrow(() -> {
            ClientOrderInputApiVo clientApiVo = new ClientOrderInputApiVo(
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString()
            );
            List<TacoInputAppVo> tacos = mock(List.class);

            ClientOrderInputAppVo actual = clientOrderInputApiToAppMapper.map(clientApiVo, tacos);

            assertThat(actual).isNotNull();
            assertThat(actual.deliveryName()).isEqualTo(clientApiVo.deliveryName());
            assertThat(actual.deliveryStreet()).isEqualTo(clientApiVo.deliveryStreet());
            assertThat(actual.deliveryCity()).isEqualTo(clientApiVo.deliveryCity());
            assertThat(actual.deliveryState()).isEqualTo(clientApiVo.deliveryState());
            assertThat(actual.deliveryZip()).isEqualTo(clientApiVo.deliveryZip());
            assertThat(actual.ccNumber()).isEqualTo(clientApiVo.ccNumber());
            assertThat(actual.ccExpiration()).isEqualTo(clientApiVo.ccExpiration());
            assertThat(actual.ccCVV()).isEqualTo(clientApiVo.ccCVV());
            assertThat(actual.tacos()).isEqualTo(tacos);
        });
    }

    @Test
    void map_whenGivenValueIsNull_thenThrowNullAssertion() {
        assertThrows(NullException.class, () -> {
            List<TacoInputAppVo> tacos = mock(List.class);

            clientOrderInputApiToAppMapper.map(null, tacos);
        });
    }
}
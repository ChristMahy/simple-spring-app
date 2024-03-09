package cmahy.webapp.resource.impl.application.taco.shop.command;

import cmahy.webapp.resource.impl.application.taco.shop.service.ReceiveAndCreateClientOrder;
import cmahy.webapp.resource.impl.application.taco.shop.vo.input.ClientOrderInputAppVo;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.ClientOrderOutputAppVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReceiveNewClientOrderCommandTest {

    @Mock
    private ReceiveAndCreateClientOrder receiveAndCreateClientOrder;

    @InjectMocks
    private ReceiveNewClientOrderCommand receiveNewClientOrderCommand;


    @Test
    void execute() {
        assertDoesNotThrow(() -> {
            var input = mock(ClientOrderInputAppVo.class);
            var output = mock(ClientOrderOutputAppVo.class);

            when(receiveAndCreateClientOrder.execute(input)).thenReturn(output);

            ClientOrderOutputAppVo actual = receiveNewClientOrderCommand.execute(input);

            assertThat(actual).isEqualTo(output);

            verify(receiveAndCreateClientOrder).execute(input);
        });
    }
}
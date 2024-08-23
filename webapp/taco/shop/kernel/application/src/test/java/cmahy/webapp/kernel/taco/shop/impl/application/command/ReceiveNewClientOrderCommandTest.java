package cmahy.webapp.kernel.taco.shop.impl.application.command;

import cmahy.webapp.taco.shop.kernel.application.command.ReceiveNewClientOrderCommand;
import cmahy.webapp.taco.shop.kernel.application.service.ReceiveAndCreateClientOrder;
import cmahy.webapp.taco.shop.kernel.vo.input.ClientOrderInputVo;
import cmahy.webapp.taco.shop.kernel.vo.output.ClientOrderOutputVo;
import cmahy.webapp.user.kernel.domain.id.UserId;
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
            var input = mock(ClientOrderInputVo.class);
            var output = mock(ClientOrderOutputVo.class);
            UserId userId = mock(UserId.class);

            when(receiveAndCreateClientOrder.execute(input, userId)).thenReturn(output);

            ClientOrderOutputVo actual = receiveNewClientOrderCommand.execute(input, userId);

            assertThat(actual).isEqualTo(output);

            verify(receiveAndCreateClientOrder).execute(input, userId);
        });
    }
}
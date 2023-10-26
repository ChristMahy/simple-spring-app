package cmahy.brokers.consumer.core.application.command.message;

import cmahy.brokers.consumer.core.application.service.message.ReceiveAMessageFromEventService;
import cmahy.brokers.consumer.core.application.vo.input.MessageInputEventAppVo;
import cmahy.brokers.consumer.core.application.vo.output.MessageOutputAppVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReceiveAMessageFromEventCommandTest {

    @Mock
    private ReceiveAMessageFromEventService save;

    @InjectMocks
    private ReceiveAMessageFromEventCommand command;

    @Mock
    private MessageInputEventAppVo inputVo;

    @Mock
    private MessageOutputAppVo outputVo;

    @Test
    void execute() {
        when(save.execute(inputVo)).thenReturn(outputVo);

        MessageOutputAppVo actual = command.execute(inputVo);

        assertThat(actual).isEqualTo(outputVo);

        verify(save).execute(inputVo);
        verifyNoMoreInteractions(save);
    }
}
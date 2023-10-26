package cmahy.brokers.consumer.core.adapter.event.message;

import cmahy.brokers.consumer.core.adapter.mapper.message.MessageInputAdapterMapper;
import cmahy.brokers.consumer.core.application.command.message.ReceiveAMessageFromEventCommand;
import cmahy.brokers.consumer.core.application.vo.input.MessageInputEventAppVo;
import cmahy.brokers.consumer.event.vo.input.MessageInputEventVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ModificationMessageListenerImplTest {

    @Mock
    private ReceiveAMessageFromEventCommand command;

    @Mock
    private MessageInputAdapterMapper mapper;

    @InjectMocks
    private ModificationMessageListenerImpl listener;

    @Mock
    private MessageInputEventVo input;

    @Mock
    private MessageInputEventAppVo inputApp;

    @Test
    void execute() {
        when(mapper.toAppVo(input)).thenReturn(inputApp);

        listener.execute(input);

        verify(mapper).toAppVo(input);
        verify(command).execute(inputApp);

        verifyNoMoreInteractions(mapper, command);
    }
}
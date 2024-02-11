package cmahy.brokers.consumer.core.adapter.message.event;

import cmahy.brokers.consumer.core.adapter.message.mapper.MessageInputAdapterMapper;
import cmahy.brokers.consumer.core.application.message.command.ReceiveAMessageFromEventCommand;
import cmahy.brokers.consumer.core.application.message.vo.input.MessageInputEventAppVo;
import cmahy.brokers.consumer.message.event.vo.input.MessageInputEventVo;
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
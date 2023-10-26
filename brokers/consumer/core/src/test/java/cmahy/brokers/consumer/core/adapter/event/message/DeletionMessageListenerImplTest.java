package cmahy.brokers.consumer.core.adapter.event.message;

import cmahy.brokers.consumer.core.adapter.mapper.message.MessageIdAdapterMapper;
import cmahy.brokers.consumer.core.application.command.message.DeleteMessageCommand;
import cmahy.brokers.consumer.core.application.vo.id.MessageAppId;
import cmahy.brokers.consumer.event.vo.id.MessageEventId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeletionMessageListenerImplTest {

    @Mock
    private DeleteMessageCommand command;

    @Mock
    private MessageIdAdapterMapper idMapper;

    @InjectMocks
    private DeletionMessageListenerImpl listener;

    @Mock
    private MessageEventId id;

    @Mock
    private MessageAppId appId;

    @Test
    void execute() {
        when(idMapper.toAppId(id)).thenReturn(appId);

        listener.execute(id);

        verify(idMapper).toAppId(id);
        verify(command).execute(appId);

        verifyNoMoreInteractions(idMapper, command);
    }
}
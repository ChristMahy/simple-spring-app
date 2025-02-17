package cmahy.simple.spring.brokers.consumer.core.adapter.message.event;

import cmahy.simple.spring.brokers.consumer.core.adapter.message.mapper.MessageIdAdapterMapper;
import cmahy.simple.spring.brokers.consumer.core.application.message.command.DeleteMessageCommand;
import cmahy.simple.spring.brokers.consumer.core.application.message.vo.id.MessageAppId;
import cmahy.simple.spring.brokers.consumer.message.event.vo.id.MessageEventId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
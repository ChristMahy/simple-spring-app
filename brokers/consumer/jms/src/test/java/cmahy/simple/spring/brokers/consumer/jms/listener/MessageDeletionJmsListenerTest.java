package cmahy.simple.spring.brokers.consumer.jms.listener;

import cmahy.simple.spring.brokers.consumer.message.event.DeletionMessageListener;
import cmahy.simple.spring.brokers.consumer.message.event.vo.id.MessageEventId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static cmahy.simple.spring.common.helper.Generator.generateAString;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageDeletionJmsListenerTest {

    @Mock
    private DeletionMessageListener listener;

    @InjectMocks
    private MessageDeletionJmsListener jmsListener;

    @Mock
    private MessageEventId id;

    @Test
    void receiveMessage() {
        assertDoesNotThrow(() -> {
            doNothing().when(listener).execute(id);

            jmsListener.receiveMessage(id);

            verify(listener).execute(id);
            verifyNoMoreInteractions(listener);
        });
    }

    @Test
    void receiveMessage_onAnyError_thenCatchIt() {
        assertDoesNotThrow(() -> {
            final RuntimeException expectedException = new RuntimeException(generateAString());

            doThrow(expectedException).when(listener).execute(id);

            jmsListener.receiveMessage(id);

            verify(listener).execute(id);
            verifyNoMoreInteractions(listener);
        });
    }
}
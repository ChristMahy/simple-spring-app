package cmahy.brokers.consumer.jms.listener;

import cmahy.brokers.consumer.message.event.ModificationMessageListener;
import cmahy.brokers.consumer.message.event.vo.input.MessageInputEventVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static cmahy.common.helper.Generator.generateAString;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageModificationJmsListenerTest {

    @Mock
    private ModificationMessageListener listener;

    @InjectMocks
    private MessageModificationJmsListener jmsListener;

    @Mock
    private MessageInputEventVo eventVo;

    @Test
    void receiveMessage() {
        assertDoesNotThrow(() -> {
            doNothing().when(listener).execute(eventVo);

            jmsListener.receiveMessage(eventVo);

            verify(listener).execute(eventVo);
            verifyNoMoreInteractions(listener);
        });
    }

    @Test
    void receiveMessage_onAnyError_thenCatchIt() {
        assertDoesNotThrow(() -> {
            final RuntimeException expectedException = new RuntimeException(generateAString());

            doThrow(expectedException).when(listener).execute(eventVo);

            jmsListener.receiveMessage(eventVo);

            verify(listener).execute(eventVo);
            verifyNoMoreInteractions(listener);
        });
    }
}
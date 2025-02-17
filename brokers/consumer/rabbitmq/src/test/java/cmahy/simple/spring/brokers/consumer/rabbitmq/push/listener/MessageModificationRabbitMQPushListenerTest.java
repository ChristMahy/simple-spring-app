package cmahy.simple.spring.brokers.consumer.rabbitmq.push.listener;

import cmahy.simple.spring.brokers.consumer.message.event.ModificationMessageListener;
import cmahy.simple.spring.brokers.consumer.message.event.vo.input.MessageInputEventVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static cmahy.simple.spring.common.helper.Generator.generateAString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageModificationRabbitMQPushListenerTest {

    @Mock
    private ModificationMessageListener listener;

    @InjectMocks
    private MessageModificationRabbitMQPushListener rabbitMQPushListener;

    @Test
    void execute() {
        assertDoesNotThrow(() -> {
            var messageInputEventVo = mock(MessageInputEventVo.class);

            rabbitMQPushListener.execute(messageInputEventVo);

            verify(listener).execute(messageInputEventVo);

            verifyNoMoreInteractions(listener);
        });
    }

    @Test
    void execute_whenAnyError_thenKeepSafeProcess() {
        assertDoesNotThrow(() -> {
            var exception = new RuntimeException(generateAString());
            var messageInputEventVo = mock(MessageInputEventVo.class);

            doThrow(exception).when(listener).execute(messageInputEventVo);

            rabbitMQPushListener.execute(messageInputEventVo);
        });
    }
}
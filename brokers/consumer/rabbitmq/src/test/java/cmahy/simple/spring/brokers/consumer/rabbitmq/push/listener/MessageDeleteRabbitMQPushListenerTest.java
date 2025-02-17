package cmahy.simple.spring.brokers.consumer.rabbitmq.push.listener;

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
class MessageDeleteRabbitMQPushListenerTest {

    @Mock
    private DeletionMessageListener listener;

    @InjectMocks
    private MessageDeleteRabbitMQPushListener rabbitMQPushListener;

    @Test
    void execute() {
        assertDoesNotThrow(() -> {
            var messageEventId = mock(MessageEventId.class);

            rabbitMQPushListener.execute(messageEventId);

            verify(listener).execute(messageEventId);

            verifyNoMoreInteractions(listener);
        });
    }

    @Test
    void execute_whenAnyError_thenKeepSafeProcess() {
        assertDoesNotThrow(() -> {
            var exception = new RuntimeException(generateAString());
            var messageEventId = mock(MessageEventId.class);

            doThrow(exception).when(listener).execute(messageEventId);

            rabbitMQPushListener.execute(messageEventId);
        });
    }
}
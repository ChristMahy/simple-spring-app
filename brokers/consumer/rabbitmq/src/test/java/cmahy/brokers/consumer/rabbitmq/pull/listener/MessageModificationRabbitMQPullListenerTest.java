package cmahy.brokers.consumer.rabbitmq.pull.listener;

import cmahy.brokers.consumer.message.event.ModificationMessageListener;
import cmahy.brokers.consumer.message.event.vo.input.MessageInputEventVo;
import cmahy.brokers.consumer.rabbitmq.config.RabbitMQQueue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;

import static cmahy.common.helper.Generator.generateAString;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageModificationRabbitMQPullListenerTest {

    @Mock
    private RabbitTemplate rabbit;

    @Mock
    private ModificationMessageListener listener;

    @InjectMocks
    private MessageModificationRabbitMQPullListener rabbitMQPullListener;

    @Test
    void execute() {
        assertDoesNotThrow(() -> {
            var messageEventInput = mock(MessageInputEventVo.class);

            when(rabbit.receiveAndConvert(
                eq(RabbitMQQueue.MESSAGE_QUEUE_NAME + ".modify"), any(ParameterizedTypeReference.class)
            )).thenReturn(messageEventInput);

            rabbitMQPullListener.execute();

            verify(rabbit).receiveAndConvert(any(), any());
            verify(listener).execute(messageEventInput);

            verifyNoMoreInteractions(rabbit, listener);
        });
    }

    @Test
    void execute_whenAnyError_thenKeepSafeProcess() {
        var exception = new RuntimeException(generateAString());

        assertDoesNotThrow(() -> {
            when(rabbit.receiveAndConvert(
                eq(RabbitMQQueue.MESSAGE_QUEUE_NAME + ".modify"), any(ParameterizedTypeReference.class)
            )).thenThrow(exception);

            rabbitMQPullListener.execute();

            verifyNoInteractions(listener);
        });
    }
}
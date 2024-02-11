package cmahy.brokers.consumer.rabbitmq.pull.listener;

import cmahy.brokers.consumer.message.event.DeletionMessageListener;
import cmahy.brokers.consumer.message.event.vo.id.MessageEventId;
import cmahy.brokers.consumer.rabbitmq.config.RabbitMQQueue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;

import static cmahy.common.helper.Generator.generateAString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageDeleteRabbitMQPullListenerTest {

    @Mock
    private RabbitTemplate rabbit;

    @Mock
    private DeletionMessageListener listener;

    @InjectMocks
    private MessageDeleteRabbitMQPullListener rabbitMQPullListener;

    @Test
    void execute() {
        assertDoesNotThrow(() -> {
            var messageEventId = mock(MessageEventId.class);

            when(rabbit.receiveAndConvert(
                eq(RabbitMQQueue.MESSAGE_QUEUE_NAME + ".delete"), any(ParameterizedTypeReference.class)
            )).thenReturn(messageEventId);

            rabbitMQPullListener.execute();

            verify(rabbit).receiveAndConvert(any(), any());
            verify(listener).execute(messageEventId);

            verifyNoMoreInteractions(rabbit, listener);
        });
    }

    @Test
    void execute_whenAnyError_thenKeepSafeProcess() {
        assertDoesNotThrow(() -> {
            var exception = new RuntimeException(generateAString());

            when(rabbit.receiveAndConvert(
                eq(RabbitMQQueue.MESSAGE_QUEUE_NAME + ".delete"), any(ParameterizedTypeReference.class)
            )).thenThrow(exception);

            rabbitMQPullListener.execute();

            verifyNoInteractions(listener);
        });
    }
}
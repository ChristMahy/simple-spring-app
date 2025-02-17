package cmahy.simple.spring.brokers.consumer.kafka.listener;

import cmahy.simple.spring.brokers.consumer.message.event.DeletionMessageListener;
import cmahy.simple.spring.brokers.consumer.message.event.vo.id.MessageEventId;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static cmahy.simple.spring.common.helper.Generator.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageDeleteKafkaListenerTest {

    @Mock
    private DeletionMessageListener listener;

    @Mock
    private ObjectMapper jsonMapper;

    @InjectMocks
    private MessageDeleteKafkaListener kafkaListener;

    @Test
    void receiveMessage() {
        assertDoesNotThrow(() -> {
            final byte[] bytes = randomBytes(randomInt(10, 500));
            final ConsumerRecord<String, byte[]> record = mock(ConsumerRecord.class);
            final MessageEventId id = mock(MessageEventId.class);

            when(jsonMapper.readValue(bytes, MessageEventId.class)).thenReturn(id);

            kafkaListener.receiveMessage(bytes, record);

            verify(jsonMapper).readValue(bytes, MessageEventId.class);
            verify(listener).execute(id);
            verifyNoMoreInteractions(jsonMapper, listener);
        });
    }

    @Test
    void receiveMessage_onAnyError_thenCatchIt() {
        assertDoesNotThrow(() -> {
            final RuntimeException expectedException = new RuntimeException(generateAString());

            final byte[] bytes = randomBytes(randomInt(10, 500));
            final ConsumerRecord<String, byte[]> record = mock(ConsumerRecord.class);
            final MessageEventId id = mock(MessageEventId.class);

            when(jsonMapper.readValue(bytes, MessageEventId.class)).thenReturn(id);
            doThrow(expectedException).when(listener).execute(id);

            kafkaListener.receiveMessage(bytes, record);
        });
    }
}
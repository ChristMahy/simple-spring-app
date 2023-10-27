package cmahy.brokers.consumer.kafka.listener;

import cmahy.brokers.consumer.event.message.ModificationMessageListener;
import cmahy.brokers.consumer.event.vo.input.MessageInputEventVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static cmahy.common.helper.Generator.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageModificationKafkaListenerTest {

    @Mock
    private ModificationMessageListener listener;

    @Mock
    private ObjectMapper jsonMapper;

    @InjectMocks
    private MessageModificationKafkaListener kafkaListener;

    @Test
    void receiveMessage() {
        assertDoesNotThrow(() -> {
            final byte[] bytes = randomBytes(randomInt(10, 500));
            final ConsumerRecord<String, byte[]> record = mock(ConsumerRecord.class);
            final MessageInputEventVo input = mock(MessageInputEventVo.class);

            when(jsonMapper.readValue(bytes, MessageInputEventVo.class)).thenReturn(input);

            kafkaListener.receiveMessage(bytes, record);

            verify(jsonMapper).readValue(bytes, MessageInputEventVo.class);
            verify(listener).execute(input);
            verifyNoMoreInteractions(jsonMapper, listener);
        });
    }

    @Test
    void receiveMessage_onAnyError_thenCatchIt() {
        assertDoesNotThrow(() -> {
            final RuntimeException expectedException = new RuntimeException(generateAString());

            final byte[] bytes = randomBytes(randomInt(10, 500));
            final ConsumerRecord<String, byte[]> record = mock(ConsumerRecord.class);
            final MessageInputEventVo input = mock(MessageInputEventVo.class);

            when(jsonMapper.readValue(bytes, MessageInputEventVo.class)).thenReturn(input);
            doThrow(expectedException).when(listener).execute(input);

            kafkaListener.receiveMessage(bytes, record);
        });
    }
}
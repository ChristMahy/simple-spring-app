package cmahy.simple.spring.brokers.consumer.core.adapter.api.message;

import cmahy.simple.spring.brokers.consumer.message.api.vo.output.MessageOutputApiVo;
import cmahy.simple.spring.brokers.consumer.core.adapter.message.mapper.MessageOutputAdapterMapper;
import cmahy.simple.spring.brokers.consumer.core.application.message.query.GetAllMessageQuery;
import cmahy.simple.spring.brokers.consumer.core.application.message.vo.output.MessageOutputAppVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.Set;

import static cmahy.simple.spring.common.helper.Generator.randomInt;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageApiImplTest {

    @Mock
    private GetAllMessageQuery getAllMessageQuery;

    @Mock
    private MessageOutputAdapterMapper messageOutputAdapterMapper;

    @InjectMocks
    private MessageApiImpl api;

    @Test
    void allMessages() {
        final int messagesSize = randomInt(50, 100);
        final Set<MessageOutputAppVo> messages = new HashSet<>(messagesSize) {{
            MessageOutputAppVo messageMocked;

            for (var cpt = 0; cpt < messagesSize; cpt++) {
                messageMocked = mock(MessageOutputAppVo.class);
                add(messageMocked);
                when(messageOutputAdapterMapper.toApiVo(eq(messageMocked))).thenReturn(mock(MessageOutputApiVo.class));
            }
        }};

        when(getAllMessageQuery.execute()).thenReturn(messages);

        ResponseEntity<Iterable<MessageOutputApiVo>> actual = api.allMessages();

        assertThat(actual).isNotNull();
        assertThat(actual.getBody()).isNotEmpty();
        assertThat(actual.getBody()).hasSize(messagesSize);

        verify(messageOutputAdapterMapper, times(messagesSize)).toApiVo(any(MessageOutputAppVo.class));
        verifyNoMoreInteractions(messageOutputAdapterMapper);
    }
}
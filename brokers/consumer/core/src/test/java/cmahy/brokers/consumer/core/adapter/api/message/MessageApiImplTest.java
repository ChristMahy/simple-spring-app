package cmahy.brokers.consumer.core.adapter.api.message;

import cmahy.brokers.consumer.api.vo.output.MessageOutputApiVo;
import cmahy.brokers.consumer.core.adapter.mapper.message.MessageOutputAdapterMapper;
import cmahy.brokers.consumer.core.application.query.message.GetAllMessageQuery;
import cmahy.brokers.consumer.core.application.vo.output.MessageOutputAppVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static cmahy.common.helper.Generator.randomInt;
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

        Set<MessageOutputApiVo> actual = api.allMessages();

        assertThat(actual).isNotEmpty();
        assertThat(actual).hasSize(messagesSize);

        verify(messageOutputAdapterMapper, times(messagesSize)).toApiVo(any(MessageOutputAppVo.class));
        verifyNoMoreInteractions(messageOutputAdapterMapper);
    }
}
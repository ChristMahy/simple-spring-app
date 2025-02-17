package cmahy.simple.spring.brokers.consumer.core.application.message.query;

import cmahy.simple.spring.brokers.consumer.core.application.message.mapper.MessageAppMapper;
import cmahy.simple.spring.brokers.consumer.core.application.message.repository.MessageRepository;
import cmahy.simple.spring.brokers.consumer.core.application.message.vo.output.MessageOutputAppVo;
import cmahy.simple.spring.brokers.consumer.core.domain.message.Message;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static cmahy.simple.spring.common.helper.Generator.randomInt;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllMessageQueryTest {

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private MessageAppMapper messageAppMapper;

    @InjectMocks
    private GetAllMessageQuery query;

    @Test
    void execute() {
        final int messageSize = randomInt(20, 80);

        Set<Message> messages = new HashSet<>(messageSize) {{
            Message messageMocked;

            for (Integer cpt = 0; cpt < messageSize; cpt++) {
                messageMocked = mock(Message.class);
                when(messageAppMapper.entityToOutput(messageMocked)).thenReturn(mock(MessageOutputAppVo.class));

                add(messageMocked);
            }
        }};

        when(messageRepository.allMessages()).thenReturn(messages);

        Set<MessageOutputAppVo> actual = query.execute();

        assertThat(actual).isNotNull();
        assertThat(actual).hasSize(messageSize);

        verify(messageRepository).allMessages();
        verify(messageAppMapper, times(messageSize)).entityToOutput(any());
        verifyNoMoreInteractions(messageRepository, messageAppMapper);
    }
}
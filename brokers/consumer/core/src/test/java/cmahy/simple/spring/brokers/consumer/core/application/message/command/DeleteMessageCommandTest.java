package cmahy.simple.spring.brokers.consumer.core.application.message.command;

import cmahy.simple.spring.brokers.consumer.core.application.message.service.DeleteByIdMessageService;
import cmahy.simple.spring.brokers.consumer.core.application.message.vo.id.MessageAppId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteMessageCommandTest {

    @Mock
    private DeleteByIdMessageService delete;

    @InjectMocks
    private DeleteMessageCommand command;

    @Test
    void execute() {
        final MessageAppId id = mock(MessageAppId.class);

        command.execute(id);

        verify(delete).execute(id);
        verifyNoMoreInteractions(delete);
    }
}
package cmahy.brokers.consumer.core.application.message.service;

import cmahy.brokers.consumer.core.application.message.mapper.MessageAppMapper;
import cmahy.brokers.consumer.core.application.message.repository.MessageRepository;
import cmahy.brokers.consumer.core.application.message.vo.input.MessageInputEventAppVo;
import cmahy.brokers.consumer.core.application.message.vo.output.MessageOutputAppVo;
import cmahy.brokers.consumer.core.domain.message.Message;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static cmahy.common.helper.Generator.randomLongEqualOrAboveZero;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReceiveAMessageFromEventServiceTest {

    @Mock
    private MessageRepository repository;

    @Mock
    private MessageAppMapper mapper;

    @InjectMocks
    private ReceiveAMessageFromEventService service;

    @Test
    void executeCreation() {
        final Long inputId = randomLongEqualOrAboveZero();
        final MessageInputEventAppVo input = mock(MessageInputEventAppVo.class, RETURNS_DEEP_STUBS);
        final Message messageFromInput = mock(Message.class);
        final Message messageFromRepoAfterCreation = mock(Message.class);
        final MessageOutputAppVo output = mock(MessageOutputAppVo.class);

        when(input.id().value()).thenReturn(inputId);
        when(repository.findById(inputId)).thenReturn(Optional.empty());
        when(mapper.inputToEntity(input)).thenReturn(messageFromInput);
        when(repository.save(messageFromInput)).thenReturn(messageFromRepoAfterCreation);
        when(mapper.entityToOutput(messageFromRepoAfterCreation)).thenReturn(output);

        final MessageOutputAppVo actual = service.execute(input);

        assertThat(actual).isEqualTo(output);

        verify(repository).findById(inputId);
        verify(repository).save(messageFromInput);

        verify(mapper).inputToEntity(input);
        verify(mapper).entityToOutput(messageFromRepoAfterCreation);

        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    void executeUpdate() {
        final Long inputId = randomLongEqualOrAboveZero();
        final MessageInputEventAppVo input = mock(MessageInputEventAppVo.class, RETURNS_DEEP_STUBS);
        final Message messageFromRepoBeforeUpdate = mock(Message.class);
        final Message messageFromRepoAfterUpdate = mock(Message.class);
        final MessageOutputAppVo output = mock(MessageOutputAppVo.class);

        when(input.id().value()).thenReturn(inputId);
        when(messageFromRepoBeforeUpdate.id()).thenReturn(inputId);
        when(repository.findById(inputId)).thenReturn(Optional.of(messageFromRepoBeforeUpdate));
        when(repository.save(any(Message.class))).thenReturn(messageFromRepoAfterUpdate);
        when(mapper.entityToOutput(messageFromRepoAfterUpdate)).thenReturn(output);

        final MessageOutputAppVo actual = service.execute(input);

        assertThat(actual).isEqualTo(output);

        verify(repository).findById(inputId);
        verify(repository).deleteById(inputId);
        verify(repository).save(any(Message.class));

        verify(mapper).entityToOutput(messageFromRepoAfterUpdate);

        verifyNoMoreInteractions(repository, mapper);
    }
}
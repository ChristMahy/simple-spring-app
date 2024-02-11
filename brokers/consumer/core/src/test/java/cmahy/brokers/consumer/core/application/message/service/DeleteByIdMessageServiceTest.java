package cmahy.brokers.consumer.core.application.message.service;

import cmahy.brokers.consumer.core.application.message.repository.MessageRepository;
import cmahy.brokers.consumer.core.application.message.vo.id.MessageAppId;
import cmahy.brokers.consumer.core.exception.message.IdShouldNotBeNullMessageException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static cmahy.common.helper.Generator.randomLongEqualOrAboveZero;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteByIdMessageServiceTest {

    @Mock
    private MessageRepository repository;

    @InjectMocks
    private DeleteByIdMessageService service;

    @Test
    void execute() {
        assertDoesNotThrow(() -> {
            final MessageAppId id = new MessageAppId(randomLongEqualOrAboveZero());

            service.execute(id);

            verify(repository).deleteById(id.value());
            verifyNoMoreInteractions(repository);
        });
    }

    @Test
    void execute_whenIdIsNull_thenAlertIdShouldNotBeNull() {
        assertThrows(IdShouldNotBeNullMessageException.class, () -> {
            service.execute(null);
        });

        verifyNoInteractions(repository);
    }
}
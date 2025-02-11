package cmahy.webapp.user.adapter.webclient.repository;

import cmahy.common.helper.Generator;
import cmahy.webapp.user.kernel.domain.id.UserId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ExternalUserRepositoryImplTest {

    @InjectMocks
    private ExternalUserRepositoryImpl externalUserRepositoryImpl;


    @Test
    void findById() {
        assertThrows(IllegalStateException.class, () -> {
            externalUserRepositoryImpl.findById(mock(UserId.class));
        });
    }

    @Test
    void findByUserName() {
        assertThrows(IllegalStateException.class, () -> {
            externalUserRepositoryImpl.findByUserName(Generator.generateAString());
        });
    }
}
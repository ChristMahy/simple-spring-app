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
class UserRepositoryImplTest {

    @InjectMocks
    private UserRepositoryImpl userRepositoryImpl;


    @Test
    void findById() {
        assertThrows(IllegalStateException.class, () -> {
            userRepositoryImpl.findById(mock(UserId.class));
        });
    }

    @Test
    void findByUserName() {
        assertThrows(IllegalStateException.class, () -> {
            userRepositoryImpl.findByUserName(Generator.generateAString());
        });
    }
}
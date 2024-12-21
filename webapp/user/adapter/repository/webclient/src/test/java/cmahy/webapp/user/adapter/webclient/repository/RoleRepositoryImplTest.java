package cmahy.webapp.user.adapter.webclient.repository;

import cmahy.common.helper.Generator;
import cmahy.webapp.user.adapter.webclient.entity.ExternalRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class RoleRepositoryImplTest {

    @InjectMocks
    private RoleRepositoryImpl roleRepositoryImpl;

    @Test
    void findByName() {
        assertThrows(IllegalStateException.class, () -> {
            roleRepositoryImpl.findByName(Generator.generateAString());
        });
    }

    @Test
    void save() {
        assertThrows(IllegalStateException.class, () -> {
            roleRepositoryImpl.save(mock(ExternalRole.class));
        });
    }
}
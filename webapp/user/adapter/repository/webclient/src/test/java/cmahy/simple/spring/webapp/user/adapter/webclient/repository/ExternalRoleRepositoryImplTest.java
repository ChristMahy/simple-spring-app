package cmahy.simple.spring.webapp.user.adapter.webclient.repository;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.user.adapter.webclient.entity.domain.ExternalRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ExternalRoleRepositoryImplTest {

    @InjectMocks
    private ExternalRoleRepositoryImpl externalRoleRepositoryImpl;

    @Test
    void findByName() {
        assertThrows(IllegalStateException.class, () -> {
            externalRoleRepositoryImpl.findByName(Generator.generateAString());
        });
    }

    @Test
    void save() {
        assertThrows(IllegalStateException.class, () -> {
            externalRoleRepositoryImpl.save(mock(ExternalRole.class));
        });
    }
}
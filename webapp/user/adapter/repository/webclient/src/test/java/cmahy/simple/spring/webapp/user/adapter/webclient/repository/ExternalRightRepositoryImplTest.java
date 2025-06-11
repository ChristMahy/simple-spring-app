package cmahy.simple.spring.webapp.user.adapter.webclient.repository;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.user.adapter.webclient.entity.domain.ExternalRight;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ExternalRightRepositoryImplTest {

    @InjectMocks
    private ExternalRightRepositoryImpl externalRightRepositoryImpl;

    @Test
    void findByName() {
        assertThrows(IllegalStateException.class, () -> {
            externalRightRepositoryImpl.findByName(Generator.generateAString());
        });
    }

    @Test
    void save() {
        assertThrows(IllegalStateException.class, () -> {
            externalRightRepositoryImpl.save(mock(ExternalRight.class));
        });
    }
}
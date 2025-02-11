package cmahy.webapp.taco.shop.adapter.webclient.repository;

import cmahy.webapp.taco.shop.adapter.webclient.entity.domain.ExternalTaco;
import cmahy.webapp.taco.shop.kernel.domain.id.IngredientId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ExternalTacoRepositoryImplTest {

    @InjectMocks
    private ExternalTacoRepositoryImpl externalTacoRepositoryImpl;

    @Test
    void save() {
        assertThrows(IllegalStateException.class, () -> {
            externalTacoRepositoryImpl.save(mock(ExternalTaco.class));
        });
    }

    @Test
    void findByIngredientId() {
        assertThrows(IllegalStateException.class, () -> {
            externalTacoRepositoryImpl.findByIngredientId(mock(IngredientId.class));
        });
    }
}
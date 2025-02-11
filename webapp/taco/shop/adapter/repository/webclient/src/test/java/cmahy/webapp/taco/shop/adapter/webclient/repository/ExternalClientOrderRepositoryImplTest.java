package cmahy.webapp.taco.shop.adapter.webclient.repository;

import cmahy.common.entity.page.EntityPageable;
import cmahy.webapp.taco.shop.adapter.webclient.entity.domain.ExternalClientOrder;
import cmahy.webapp.user.adapter.webclient.entity.domain.ExternalUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ExternalClientOrderRepositoryImplTest {

    @InjectMocks
    private ExternalClientOrderRepositoryImpl externalClientOrderRepositoryImpl;

    @Test
    void getByUser() {
        assertThrows(IllegalStateException.class, () -> {
            externalClientOrderRepositoryImpl.getByUser(mock(ExternalUser.class), mock(EntityPageable.class));
        });
    }

    @Test
    void save() {
        assertThrows(IllegalStateException.class, () -> {
            externalClientOrderRepositoryImpl.save(mock(ExternalClientOrder.class));
        });
    }
}
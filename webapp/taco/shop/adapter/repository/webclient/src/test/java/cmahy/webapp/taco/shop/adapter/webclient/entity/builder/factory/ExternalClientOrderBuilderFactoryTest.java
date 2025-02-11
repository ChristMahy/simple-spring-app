package cmahy.webapp.taco.shop.adapter.webclient.entity.builder.factory;

import cmahy.webapp.taco.shop.adapter.webclient.entity.domain.ExternalClientOrder;
import cmahy.webapp.taco.shop.adapter.webclient.entity.builder.ExternalClientOrderBuilder;
import cmahy.webapp.taco.shop.kernel.domain.ClientOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.RETURNS_SELF;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ExternalClientOrderBuilderFactoryTest {

    @InjectMocks
    private ExternalClientOrderBuilderFactory externalClientOrderBuilderFactory;

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            ExternalClientOrderBuilder actual = externalClientOrderBuilderFactory.create();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(ExternalClientOrderBuilder.class);
        });
    }

    @Test
    void createWithOriginal() {
        assertDoesNotThrow(() -> {
            ExternalClientOrder clientOrder = mock(ExternalClientOrder.class, RETURNS_SELF);

            ExternalClientOrderBuilder actual = externalClientOrderBuilderFactory.create(clientOrder);

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(ExternalClientOrderBuilder.class);

            ClientOrder actualBuilderResult = actual.build();

            assertThat(actualBuilderResult).isSameAs(clientOrder);
        });
    }
}
package cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.factory.test;

import cmahy.simple.spring.webapp.taco.shop.kernel.domain.ClientOrder;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.ClientOrderStub;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.ClientOrderBuilderStub;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.factory.ClientOrderBuilderFactoryStub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.RETURNS_SELF;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ClientOrderBuilderFactoryStubTest {

    @InjectMocks
    private ClientOrderBuilderFactoryStub clientOrderBuilderFactoryStub;

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            ClientOrderBuilderStub actual = clientOrderBuilderFactoryStub.create();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(ClientOrderBuilderStub.class);
        });
    }

    @Test
    void createWithOriginal() {
        assertDoesNotThrow(() -> {
            ClientOrderStub clientOrder = mock(ClientOrderStub.class, RETURNS_SELF);

            ClientOrderBuilderStub actual = clientOrderBuilderFactoryStub.create(clientOrder);

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(ClientOrderBuilderStub.class);

            ClientOrder actualBuilderResult = actual.build();

            assertThat(actualBuilderResult).isSameAs(clientOrder);
        });
    }
}
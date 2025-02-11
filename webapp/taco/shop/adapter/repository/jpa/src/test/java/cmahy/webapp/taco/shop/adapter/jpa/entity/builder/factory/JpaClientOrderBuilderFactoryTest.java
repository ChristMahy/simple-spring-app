package cmahy.webapp.taco.shop.adapter.jpa.entity.builder.factory;

import cmahy.webapp.taco.shop.adapter.jpa.entity.domain.JpaClientOrder;
import cmahy.webapp.taco.shop.adapter.jpa.entity.builder.JpaClientOrderBuilder;
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
class JpaClientOrderBuilderFactoryTest {

    @InjectMocks
    private JpaClientOrderBuilderFactory jpaClientOrderBuilderFactory;

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            JpaClientOrderBuilder actual = jpaClientOrderBuilderFactory.create();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(JpaClientOrderBuilder.class);
        });
    }

    @Test
    void createWithOriginal() {
        assertDoesNotThrow(() -> {
            JpaClientOrder clientOrder = mock(JpaClientOrder.class, RETURNS_SELF);

            JpaClientOrderBuilder actual = jpaClientOrderBuilderFactory.create(clientOrder);

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(JpaClientOrderBuilder.class);

            ClientOrder actualBuilderResult = actual.build();

            assertThat(actualBuilderResult).isSameAs(clientOrder);
        });
    }
}
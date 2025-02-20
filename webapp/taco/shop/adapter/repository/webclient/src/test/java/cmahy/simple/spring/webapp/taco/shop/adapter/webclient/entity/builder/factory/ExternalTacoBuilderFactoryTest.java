package cmahy.simple.spring.webapp.taco.shop.adapter.webclient.entity.builder.factory;

import cmahy.simple.spring.webapp.taco.shop.adapter.webclient.entity.domain.ExternalTaco;
import cmahy.simple.spring.webapp.taco.shop.adapter.webclient.entity.builder.ExternalTacoBuilder;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Taco;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.RETURNS_SELF;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ExternalTacoBuilderFactoryTest {

    @InjectMocks
    private ExternalTacoBuilderFactory externalTacoBuilderFactory;

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            ExternalTacoBuilder actual = externalTacoBuilderFactory.create();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(ExternalTacoBuilder.class);
        });
    }

    @Test
    void createWithOriginal() {
        assertDoesNotThrow(() -> {
            ExternalTaco original = mock(ExternalTaco.class, RETURNS_SELF);

            ExternalTacoBuilder actual = externalTacoBuilderFactory.create(original);

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(ExternalTacoBuilder.class);

            Taco actualBuilderResult = actual.build();

            assertThat(actualBuilderResult).isSameAs(original);
        });
    }
}
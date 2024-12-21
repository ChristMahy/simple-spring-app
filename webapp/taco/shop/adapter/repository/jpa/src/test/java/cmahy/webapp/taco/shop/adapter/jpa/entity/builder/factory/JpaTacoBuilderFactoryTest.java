package cmahy.webapp.taco.shop.adapter.jpa.entity.builder.factory;

import cmahy.webapp.taco.shop.adapter.jpa.entity.JpaTaco;
import cmahy.webapp.taco.shop.adapter.jpa.entity.builder.JpaTacoBuilder;
import cmahy.webapp.taco.shop.kernel.domain.Taco;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.RETURNS_SELF;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class JpaTacoBuilderFactoryTest {

    @InjectMocks
    private JpaTacoBuilderFactory jpaTacoBuilderFactory;

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            JpaTacoBuilder actual = jpaTacoBuilderFactory.create();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(JpaTacoBuilder.class);
        });
    }

    @Test
    void createWithOriginal() {
        assertDoesNotThrow(() -> {
            JpaTaco original = mock(JpaTaco.class, RETURNS_SELF);

            JpaTacoBuilder actual = jpaTacoBuilderFactory.create(original);

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(JpaTacoBuilder.class);

            Taco actualBuilderResult = actual.build();

            assertThat(actualBuilderResult).isSameAs(original);
        });
    }
}
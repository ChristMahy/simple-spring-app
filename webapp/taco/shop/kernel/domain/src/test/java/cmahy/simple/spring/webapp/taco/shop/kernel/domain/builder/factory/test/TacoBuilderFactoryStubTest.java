package cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.factory.test;

import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Taco;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.TacoStub;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.TacoBuilderStub;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.factory.TacoBuilderFactoryStub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.RETURNS_SELF;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class TacoBuilderFactoryStubTest {

    @InjectMocks
    private TacoBuilderFactoryStub tacoBuilderFactoryStub;

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            TacoBuilderStub actual = tacoBuilderFactoryStub.create();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(TacoBuilderStub.class);
        });
    }

    @Test
    void createWithOriginal() {
        assertDoesNotThrow(() -> {
            TacoStub original = mock(TacoStub.class, RETURNS_SELF);

            TacoBuilderStub actual = tacoBuilderFactoryStub.create(original);

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(TacoBuilderStub.class);

            Taco actualBuilderResult = actual.build();

            assertThat(actualBuilderResult).isSameAs(original);
        });
    }
}
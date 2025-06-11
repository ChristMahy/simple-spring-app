package cmahy.simple.spring.webapp.user.kernel.domain.builder.factory.test;

import cmahy.simple.spring.webapp.user.kernel.domain.RightStub;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.RightBuilderStub;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.factory.RightBuilderFactoryStub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.RETURNS_SELF;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class RightBuilderFactoryStubTest {

    @InjectMocks
    private RightBuilderFactoryStub builderFactory;

    @Test
    void create() {
        assertDoesNotThrow(() -> {


            RightBuilderStub actual = builderFactory.create();


            assertThat(actual)
                .isNotNull()
                .isInstanceOf(RightBuilderStub.class);

            RightStub actualBuilt = actual.build();

            assertThat(actualBuilt).isNotNull();
        });
    }

    @Test
    void createWithOriginalRole() {
        assertDoesNotThrow(() -> {

            RightStub right = mock(RightStub.class, RETURNS_SELF);


            RightBuilderStub actual = builderFactory.create(right);


            assertThat(actual)
                .isNotNull()
                .isInstanceOf(RightBuilderStub.class);

            RightStub actualBuilt = actual.build();

            assertThat(actualBuilt).isSameAs(right);
        });
    }
}

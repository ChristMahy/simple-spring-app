package cmahy.simple.spring.webapp.user.kernel.domain.builder.factory.test;

import cmahy.simple.spring.webapp.user.kernel.domain.UserStub;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.UserBuilderStub;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.factory.UserBuilderFactoryStub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.RETURNS_SELF;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class UserBuilderFactoryStubTest {

    @InjectMocks
    private UserBuilderFactoryStub userBuilderFactoryStub;

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            UserBuilderStub actual = userBuilderFactoryStub.create();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(UserBuilderStub.class);

            UserStub actualBuilt = actual.build();

            assertThat(actualBuilt).isNotNull();
        });
    }

    @Test
    void createWithOriginalRole() {
        assertDoesNotThrow(() -> {
            UserStub user = mock(UserStub.class, RETURNS_SELF);

            UserBuilderStub actual = userBuilderFactoryStub.create(user);

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(UserBuilderStub.class);

            UserStub actualBuilderResult = actual.build();

            assertThat(actualBuilderResult).isSameAs(user);
        });
    }
}
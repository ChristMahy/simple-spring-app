package cmahy.simple.spring.webapp.user.kernel.domain.builder.factory.test;

import cmahy.simple.spring.webapp.user.kernel.domain.UserSecurity;
import cmahy.simple.spring.webapp.user.kernel.domain.UserSecurityStub;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.UserSecurityBuilderStub;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.factory.UserSecurityBuilderFactoryStub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.RETURNS_SELF;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class UserSecurityBuilderFactoryStubTest {

    @InjectMocks
    private UserSecurityBuilderFactoryStub userSecurityBuilderFactory;

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            UserSecurityBuilderStub actual = userSecurityBuilderFactory.create();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(UserSecurityBuilderStub.class);

            UserSecurityStub actualBuilt = actual.build();

            assertThat(actualBuilt).isNotNull();
        });
    }

    @Test
    void createWithOriginalRole() {
        assertDoesNotThrow(() -> {
            UserSecurityStub userSecurity = mock(UserSecurityStub.class, RETURNS_SELF);

            UserSecurityBuilderStub actual = userSecurityBuilderFactory.create(userSecurity);

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(UserSecurityBuilderStub.class);

            UserSecurity actualResultBuilder = actual.build();

            assertThat(actualResultBuilder).isSameAs(userSecurity);
        });
    }
}
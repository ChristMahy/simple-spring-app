package cmahy.webapp.user.adapter.jpa.entity.builder.factory;

import cmahy.webapp.user.adapter.jpa.entity.JpaUserSecurity;
import cmahy.webapp.user.adapter.jpa.entity.builder.JpaUserSecurityBuilder;
import cmahy.webapp.user.kernel.domain.UserSecurity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.RETURNS_SELF;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class JpaUserSecurityBuilderFactoryTest {

    @InjectMocks
    private JpaUserSecurityBuilderFactory jpaUserSecurityBuilderFactory;

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            JpaUserSecurityBuilder actual = jpaUserSecurityBuilderFactory.create();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(JpaUserSecurityBuilder.class);
        });
    }

    @Test
    void createWithOriginalUserSecurity() {
        assertDoesNotThrow(() -> {
            JpaUserSecurity userSecurity = mock(JpaUserSecurity.class, RETURNS_SELF);

            JpaUserSecurityBuilder actual = jpaUserSecurityBuilderFactory.create(userSecurity);

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(JpaUserSecurityBuilder.class);

            UserSecurity actualResultBuilder = actual.build();

            assertThat(actualResultBuilder).isSameAs(userSecurity);
        });
    }
}
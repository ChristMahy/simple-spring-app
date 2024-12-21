package cmahy.webapp.user.adapter.jpa.entity.builder.factory;

import cmahy.webapp.user.adapter.jpa.entity.JpaRole;
import cmahy.webapp.user.adapter.jpa.entity.builder.JpaRoleBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.RETURNS_SELF;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class JpaRoleBuilderFactoryTest {

    @InjectMocks
    private JpaRoleBuilderFactory jpaRoleBuilderFactory;

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            JpaRoleBuilder actual = jpaRoleBuilderFactory.create();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(JpaRoleBuilder.class);
        });
    }

    @Test
    void createWithOriginalRole() {
        assertDoesNotThrow(() -> {
            JpaRole role = mock(JpaRole.class, RETURNS_SELF);

            JpaRoleBuilder actual = jpaRoleBuilderFactory.create(role);

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(JpaRoleBuilder.class);

            JpaRole actualBuilderResult = actual.build();

            assertThat(actualBuilderResult).isSameAs(role);
        });
    }
}
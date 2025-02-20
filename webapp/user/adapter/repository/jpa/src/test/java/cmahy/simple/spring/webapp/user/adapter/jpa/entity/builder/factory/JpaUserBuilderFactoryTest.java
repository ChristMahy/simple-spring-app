package cmahy.simple.spring.webapp.user.adapter.jpa.entity.builder.factory;

import cmahy.simple.spring.webapp.user.adapter.jpa.entity.builder.JpaUserBuilder;
import cmahy.simple.spring.webapp.user.adapter.jpa.entity.domain.JpaUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.RETURNS_SELF;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class JpaUserBuilderFactoryTest {

    @InjectMocks
    private JpaUserBuilderFactory jpaUserBuilderFactory;

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            JpaUserBuilder actual = jpaUserBuilderFactory.create();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(JpaUserBuilder.class);

            JpaUser actualBuilt = actual.build();

            assertThat(actualBuilt).isNotNull();
        });
    }

    @Test
    void createWithOriginalUser() {
        assertDoesNotThrow(() -> {
            JpaUser user = mock(JpaUser.class, RETURNS_SELF);

            JpaUserBuilder actual = jpaUserBuilderFactory.create(user);

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(JpaUserBuilder.class);

            JpaUser actualBuilderResult = actual.build();

            assertThat(actualBuilderResult).isSameAs(user);
        });
    }
}
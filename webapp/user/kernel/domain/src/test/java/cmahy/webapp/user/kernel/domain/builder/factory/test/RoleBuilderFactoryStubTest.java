package cmahy.webapp.user.kernel.domain.builder.factory.test;

import cmahy.webapp.user.kernel.domain.RoleStub;
import cmahy.webapp.user.kernel.domain.builder.RoleBuilderStub;
import cmahy.webapp.user.kernel.domain.builder.factory.RoleBuilderFactoryStub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.RETURNS_SELF;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class RoleBuilderFactoryStubTest {

    @InjectMocks
    private RoleBuilderFactoryStub roleBuilderFactoryStub;

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            RoleBuilderStub actual = roleBuilderFactoryStub.create();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(RoleBuilderStub.class);
        });
    }

    @Test
    void createWithOriginalRole() {
        assertDoesNotThrow(() -> {
            RoleStub role = mock(RoleStub.class, RETURNS_SELF);

            RoleBuilderStub actual = roleBuilderFactoryStub.create(role);

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(RoleBuilderStub.class);

            RoleStub actualBuilderResult = actual.build();

            assertThat(actualBuilderResult).isSameAs(role);
        });
    }
}
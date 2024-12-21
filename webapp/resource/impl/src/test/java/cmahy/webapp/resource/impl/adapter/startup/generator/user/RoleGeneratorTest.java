package cmahy.webapp.resource.impl.adapter.startup.generator.user;

import cmahy.webapp.user.kernel.application.repository.RoleRepository;
import cmahy.webapp.user.kernel.domain.Role;
import cmahy.webapp.user.kernel.domain.builder.RoleBuilder;
import cmahy.webapp.user.kernel.domain.builder.factory.RoleBuilderFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.ApplicationArguments;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Answers.RETURNS_SELF;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleGeneratorTest {

    @Mock
    private RoleRepository<Role> roleRepository;

    @Mock
    private RoleBuilderFactory<Role> roleBuilderFactory;

    @InjectMocks
    private RoleGenerator roleGenerator;

    @Mock
    private ApplicationArguments applicationArguments;

    @Mock(answer = RETURNS_SELF)
    private RoleBuilder<Role> roleBuilder;

    @Mock
    private Role role;

    @Test
    void run() {
        assertDoesNotThrow(() -> {
            when(roleBuilderFactory.create()).thenReturn(roleBuilder);
            when(roleBuilder.build()).thenReturn(role);

            roleGenerator.run(applicationArguments);

            verify(roleRepository, times(2)).save(role);
        });
    }
}
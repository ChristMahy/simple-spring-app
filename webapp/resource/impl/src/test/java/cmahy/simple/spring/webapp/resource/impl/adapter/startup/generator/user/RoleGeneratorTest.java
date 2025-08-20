package cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.user;

import cmahy.simple.spring.webapp.user.kernel.application.repository.RightRepository;
import cmahy.simple.spring.webapp.user.kernel.application.repository.RoleRepository;
import cmahy.simple.spring.webapp.user.kernel.domain.Right;
import cmahy.simple.spring.webapp.user.kernel.domain.Role;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.RoleBuilder;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.factory.RoleBuilderFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.context.event.ApplicationStartedEvent;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Answers.RETURNS_SELF;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleGeneratorTest {

    @Mock
    private RightRepository<Right> rightRepository;

    @Mock
    private RoleRepository<Role> roleRepository;

    @Mock
    private RoleBuilderFactory<Role> roleBuilderFactory;

    @InjectMocks
    private RoleGenerator roleGenerator;

    @Mock
    private ApplicationStartedEvent applicationArguments;

    @Mock(answer = RETURNS_SELF)
    private RoleBuilder<Role> roleBuilder;

    @Mock
    private Role role;

    @Test
    void onApplicationEvent() {
        assertDoesNotThrow(() -> {

            when(rightRepository.findByName(anyString())).thenAnswer(_ -> Optional.of(mock(Right.class)));
            when(roleBuilderFactory.create()).thenReturn(roleBuilder);
            when(roleBuilder.build()).thenReturn(role);

            roleGenerator.onApplicationEvent(applicationArguments);

            verify(roleRepository, times(2)).save(role);

        });
    }
}
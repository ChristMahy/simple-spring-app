package cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.user;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.ApplicationProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.start.OnStartProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.start.ResourcesProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.user.vo.input.RoleGeneratorInputVo;
import cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.user.vo.input.UserSecurityGeneratorInputVo;
import cmahy.simple.spring.webapp.user.kernel.application.repository.RoleRepository;
import cmahy.simple.spring.webapp.user.kernel.application.repository.UserSecurityRepository;
import cmahy.simple.spring.webapp.user.kernel.domain.*;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.UserSecurityBuilder;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.factory.UserSecurityBuilderFactory;
import cmahy.simple.spring.webapp.user.kernel.exception.RoleNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Answers.RETURNS_SELF;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserSecurityGeneratorTest {

    @Mock
    private UserSecurityRepository<UserSecurity> userSecurityRepository;

    @Mock
    private RoleRepository<Role> roleRepository;

    @Mock
    private UserSecurityBuilderFactory<UserSecurity> userSecurityBuilderFactory;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ApplicationProperties applicationProperties;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private UserSecurityGenerator userSecurityGenerator;

    @Mock(answer = RETURNS_SELF)
    private UserSecurityBuilder<UserSecurity> userSecurityBuilder;

    @Mock
    private ApplicationStartedEvent applicationArguments;

    @Mock
    private OnStartProperties onStartProperties;

    @Mock
    private ResourcesProperties resourcesProperties;

    @Mock
    private Resource resource;

    @Mock
    private InputStream inputStream;

    @BeforeEach
    void setUp() {

        when(onStartProperties.resources()).thenReturn(resourcesProperties);
        when(applicationProperties.onStart()).thenReturn(onStartProperties);

    }

    @Test
    void onApplicationEvent() throws IOException {

        UserSecurityGeneratorInputVo userSecurityInput = mock(UserSecurityGeneratorInputVo.class);
        RoleGeneratorInputVo roleInput = mock(RoleGeneratorInputVo.class);

        UserSecurity userSecurity = mock(UserSecurity.class);
        Role role = mock(Role.class);

        String userName = Generator.generateAString();
        AuthProvider authProvider = Generator.randomEnum(AuthProvider.class);
        String roleName = Generator.generateAString();
        String password = Generator.generateAString();

        when(userSecurityInput.userName()).thenReturn(userName);
        when(userSecurityInput.authProvider()).thenReturn(authProvider);
        when(userSecurityInput.roles()).thenReturn(Set.of(roleInput));

        when(roleInput.name()).thenReturn(roleName);

        when(passwordEncoder.encode(userName)).thenReturn(password);

        when(userSecurityBuilderFactory.create()).thenReturn(userSecurityBuilder);
        when(userSecurityBuilder.build()).thenReturn(userSecurity);

        when(resourcesProperties.usersSecurities()).thenReturn(resource);
        when(resource.exists()).thenReturn(true);
        when(resource.getInputStream()).thenReturn(inputStream);

        when(objectMapper.readValue(eq(inputStream), any(TypeReference.class))).thenReturn(List.of(userSecurityInput));

        when(userSecurityRepository.findByUserNameAndAuthProvider(userName, authProvider)).thenReturn(Optional.empty());
        when(roleRepository.findByName(roleName)).thenReturn(Optional.of(role));

        when(userSecurityRepository.save(userSecurity)).thenReturn(userSecurity);


        assertDoesNotThrow(() -> userSecurityGenerator.onApplicationEvent(applicationArguments));


        verify(userSecurityBuilder).roles(Set.of(role));
        verify(userSecurityRepository).save(userSecurity);

    }

    @Test
    void onApplicationEvent_whenRoleNotFound_thenThrowException() throws IOException {

        UserSecurityGeneratorInputVo userSecurityInput = mock(UserSecurityGeneratorInputVo.class);
        RoleGeneratorInputVo roleInput = mock(RoleGeneratorInputVo.class);

        String userName = Generator.generateAString();
        AuthProvider authProvider = Generator.randomEnum(AuthProvider.class);
        String roleName = Generator.generateAString();

        when(userSecurityInput.userName()).thenReturn(userName);
        when(userSecurityInput.authProvider()).thenReturn(authProvider);
        when(userSecurityInput.roles()).thenReturn(Set.of(roleInput));

        when(roleInput.name()).thenReturn(roleName);

        when(resourcesProperties.usersSecurities()).thenReturn(resource);
        when(resource.exists()).thenReturn(true);
        when(resource.getInputStream()).thenReturn(inputStream);

        when(objectMapper.readValue(eq(inputStream), any(TypeReference.class))).thenReturn(List.of(userSecurityInput));

        when(userSecurityRepository.findByUserNameAndAuthProvider(userName, authProvider)).thenReturn(Optional.empty());
        when(roleRepository.findByName(roleName)).thenReturn(Optional.empty());


        RuntimeException exception = assertThrows(RuntimeException.class, () -> userSecurityGenerator.onApplicationEvent(applicationArguments));


        assertThat(exception)
            .isNotNull()
            .extracting(RuntimeException::getCause)
            .isInstanceOf(RoleNotFoundException.class);

        verify(userSecurityRepository, never()).save(any(UserSecurity.class));
        verifyNoInteractions(userSecurityBuilder, userSecurityBuilderFactory, passwordEncoder);

    }

    @Test
    void onApplicationEvent_whenResourceDoesNotExist_thenDontExecute() {

        when(resourcesProperties.usersSecurities()).thenReturn(resource);
        when(resource.exists()).thenReturn(false);


        userSecurityGenerator.onApplicationEvent(applicationArguments);


        verifyNoInteractions(
            userSecurityRepository,
            roleRepository,
            userSecurityBuilderFactory,
            passwordEncoder,
            objectMapper
        );

    }

    @Test
    void onApplicationEvent_whenResourceIsNull_thenDontExecute() {

        when(resourcesProperties.usersSecurities()).thenReturn(null);


        userSecurityGenerator.onApplicationEvent(applicationArguments);


        verifyNoInteractions(
            userSecurityRepository,
            roleRepository,
            userSecurityBuilderFactory,
            passwordEncoder,
            objectMapper
        );

    }

}
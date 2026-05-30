package cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.user;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.ApplicationProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.start.OnStartProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.start.ResourcesProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.user.vo.input.RoleGeneratorInputVo;
import cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.user.vo.input.UserGeneratorInputVo;
import cmahy.simple.spring.webapp.user.kernel.application.repository.RoleRepository;
import cmahy.simple.spring.webapp.user.kernel.application.repository.UserRepository;
import cmahy.simple.spring.webapp.user.kernel.domain.Role;
import cmahy.simple.spring.webapp.user.kernel.domain.User;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.UserBuilder;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.factory.UserBuilderFactory;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserGeneratorTest {

    @Mock
    private UserRepository<User> userRepository;

    @Mock
    private RoleRepository<Role> roleRepository;

    @Mock
    private UserBuilderFactory<User> userBuilderFactory;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ApplicationProperties applicationProperties;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private UserGenerator userGenerator;

    @Mock(answer = RETURNS_SELF)
    private UserBuilder<User> userBuilder;

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

        UserGeneratorInputVo userInput = mock(UserGeneratorInputVo.class);
        RoleGeneratorInputVo roleInput = mock(RoleGeneratorInputVo.class);

        User user = mock(User.class);
        Role role = mock(Role.class);

        String userName = Generator.generateAString();
        String roleName = Generator.generateAString();
        String password = Generator.generateAString();

        when(userInput.userName()).thenReturn(userName);
        when(userInput.roles()).thenReturn(Set.of(roleInput));

        when(roleInput.name()).thenReturn(roleName);

        when(passwordEncoder.encode(userName)).thenReturn(password);

        when(userBuilderFactory.create()).thenReturn(userBuilder);
        when(userBuilder.build()).thenReturn(user);

        when(resourcesProperties.users()).thenReturn(resource);
        when(resource.exists()).thenReturn(true);
        when(resource.getInputStream()).thenReturn(inputStream);

        when(objectMapper.readValue(eq(inputStream), any(TypeReference.class))).thenReturn(List.of(userInput));

        when(userRepository.findByUserName(userName)).thenReturn(Optional.empty());
        when(roleRepository.findByName(roleName)).thenReturn(Optional.of(role));

        when(userRepository.save(user)).thenReturn(user);


        assertDoesNotThrow(() -> userGenerator.onApplicationEvent(applicationArguments));


        verify(userBuilder).roles(Set.of(role));
        verify(userRepository).save(user);

    }

    @Test
    void onApplicationEvent_whenRoleNotFound_thenThrowException() throws IOException {

        UserGeneratorInputVo userInput = mock(UserGeneratorInputVo.class);
        RoleGeneratorInputVo roleInput = mock(RoleGeneratorInputVo.class);

        String userName = Generator.generateAString();
        String roleName = Generator.generateAString();

        when(userInput.userName()).thenReturn(userName);
        when(userInput.roles()).thenReturn(Set.of(roleInput));

        when(roleInput.name()).thenReturn(roleName);

        when(resourcesProperties.users()).thenReturn(resource);
        when(resource.exists()).thenReturn(true);
        when(resource.getInputStream()).thenReturn(inputStream);

        when(objectMapper.readValue(eq(inputStream), any(TypeReference.class))).thenReturn(List.of(userInput));

        when(userRepository.findByUserName(userName)).thenReturn(Optional.empty());
        when(roleRepository.findByName(roleName)).thenReturn(Optional.empty());


        RuntimeException exception = assertThrows(RuntimeException.class, () -> userGenerator.onApplicationEvent(applicationArguments));


        assertThat(exception)
            .isNotNull()
            .extracting(RuntimeException::getCause)
            .isInstanceOf(RoleNotFoundException.class);

        verify(userRepository, never()).save(any(User.class));
        verifyNoInteractions(userBuilder, userBuilderFactory, passwordEncoder);

    }

    @Test
    void onApplicationEvent_whenResourceDoesNotExist_thenDontExecute() {

        when(resourcesProperties.users()).thenReturn(resource);
        when(resource.exists()).thenReturn(false);


        userGenerator.onApplicationEvent(applicationArguments);


        verifyNoInteractions(
            userRepository,
            roleRepository,
            userBuilderFactory,
            passwordEncoder,
            objectMapper
        );

    }

    @Test
    void onApplicationEvent_whenResourceIsNull_thenDontExecute() {

        when(resourcesProperties.users()).thenReturn(null);


        userGenerator.onApplicationEvent(applicationArguments);


        verifyNoInteractions(
            userRepository,
            roleRepository,
            userBuilderFactory,
            passwordEncoder,
            objectMapper
        );

    }
}
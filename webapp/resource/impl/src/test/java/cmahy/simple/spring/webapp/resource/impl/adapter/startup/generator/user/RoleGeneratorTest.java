package cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.user;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.ApplicationProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.start.OnStartProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.start.ResourcesProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.user.vo.input.RightGeneratorInputVo;
import cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.user.vo.input.RoleGeneratorInputVo;
import cmahy.simple.spring.webapp.user.kernel.application.repository.RightRepository;
import cmahy.simple.spring.webapp.user.kernel.application.repository.RoleRepository;
import cmahy.simple.spring.webapp.user.kernel.domain.Right;
import cmahy.simple.spring.webapp.user.kernel.domain.Role;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.RoleBuilder;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.factory.RoleBuilderFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.core.io.Resource;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

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

    @Mock
    private ApplicationProperties applicationProperties;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private RoleGenerator roleGenerator;

    @Mock
    private ApplicationStartedEvent applicationArguments;

    @Mock(answer = RETURNS_SELF)
    private RoleBuilder<Role> roleBuilder;

    @Mock
    private Role role;

    @Mock
    private Right right;

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

        RoleGeneratorInputVo roleInput = mock(RoleGeneratorInputVo.class);
        RightGeneratorInputVo rightInput = mock(RightGeneratorInputVo.class);

        String rightName = Generator.generateAString();

        when(rightInput.name()).thenReturn(rightName);

        when(roleInput.rights()).thenReturn(Set.of(rightInput));

        when(resourcesProperties.roles()).thenReturn(resource);
        when(resource.exists()).thenReturn(true);
        when(resource.getInputStream()).thenReturn(inputStream);

        when(objectMapper.readValue(eq(inputStream), any(TypeReference.class))).thenReturn(List.of(roleInput));

        assertDoesNotThrow(() -> {

            when(rightRepository.findByName(rightName)).thenReturn(Optional.of(right));

            when(roleBuilderFactory.create()).thenReturn(roleBuilder);
            when(roleBuilder.build()).thenReturn(role);

            when(roleRepository.save(role)).thenReturn(role);


            roleGenerator.onApplicationEvent(applicationArguments);


            verify(roleRepository).save(role);
            verify(roleBuilder).rights(Set.of(right));

        });

    }

    @Test
    void onApplicationEvent_whenResourceDoesNotExist_thenDontExecute() {

        when(resourcesProperties.roles()).thenReturn(resource);
        when(resource.exists()).thenReturn(false);


        roleGenerator.onApplicationEvent(applicationArguments);


        verifyNoInteractions(rightRepository, roleRepository, roleBuilderFactory, roleBuilder, objectMapper);

    }

    @Test
    void onApplicationEvent_whenResourceIsNull_thenDontExecute() {

        when(resourcesProperties.roles()).thenReturn(null);


        roleGenerator.onApplicationEvent(applicationArguments);


        verifyNoInteractions(rightRepository, roleRepository, roleBuilderFactory, roleBuilder, objectMapper);

    }

}
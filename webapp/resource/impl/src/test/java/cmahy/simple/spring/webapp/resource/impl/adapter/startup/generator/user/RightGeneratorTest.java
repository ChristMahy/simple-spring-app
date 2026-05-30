package cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.user;

import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.ApplicationProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.start.OnStartProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.start.ResourcesProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.user.vo.input.RightGeneratorInputVo;
import cmahy.simple.spring.webapp.user.kernel.application.repository.RightRepository;
import cmahy.simple.spring.webapp.user.kernel.domain.Right;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.RightBuilder;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.factory.RightBuilderFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.core.io.Resource;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RightGeneratorTest {

    @Mock
    private RightRepository<Right> rightRepository;

    @Mock
    private RightBuilderFactory<Right> rightBuilderFactory;

    @Mock
    private ApplicationProperties applicationProperties;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private RightGenerator rightGenerator;

    @Mock
    private ApplicationStartedEvent applicationArguments;

    @Mock(answer = Answers.RETURNS_SELF)
    private RightBuilder<Right> rightBuilder;

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

        RightGeneratorInputVo rightInput = mock(RightGeneratorInputVo.class);

        when(resourcesProperties.rights()).thenReturn(resource);
        when(resource.exists()).thenReturn(true);
        when(resource.getInputStream()).thenReturn(inputStream);

        when(objectMapper.readValue(eq(inputStream), any(TypeReference.class))).thenReturn(List.of(rightInput));

        assertDoesNotThrow(() -> {

            when(rightBuilderFactory.create()).thenReturn(rightBuilder);
            when(rightBuilder.build()).thenReturn(right);

            when(rightRepository.save(right)).thenReturn(right);


            rightGenerator.onApplicationEvent(applicationArguments);


            verify(rightRepository).save(right);

        });
    }

    @Test
    void onApplicationEvent_whenResourceDoesNotExist_thenDontExecute() {

        when(resourcesProperties.rights()).thenReturn(resource);
        when(resource.exists()).thenReturn(false);


        rightGenerator.onApplicationEvent(applicationArguments);


        verifyNoInteractions(rightRepository, rightBuilderFactory, rightBuilder, objectMapper);
    }

    @Test
    void onApplicationEvent_whenResourceIsNull_thenDontExecute() {

        when(resourcesProperties.rights()).thenReturn(null);


        rightGenerator.onApplicationEvent(applicationArguments);


        verifyNoInteractions(rightRepository, rightBuilderFactory, rightBuilder, objectMapper);
    }

}
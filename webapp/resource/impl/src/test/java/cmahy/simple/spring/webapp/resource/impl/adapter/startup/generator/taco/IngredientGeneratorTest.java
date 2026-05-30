package cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.taco;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.ApplicationProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.start.OnStartProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.start.ResourcesProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.taco.vo.input.IngredientGeneratorInputVo;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.command.CreateIngredientCommand;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.repository.IngredientRepository;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.IngredientType;
import cmahy.simple.spring.webapp.taco.shop.kernel.vo.input.IngredientCreateInputVo;
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

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientGeneratorTest {

    @Mock
    private CreateIngredientCommand createIngredientCommand;

    @Mock
    private IngredientRepository<? extends Ingredient> ingredientRepository;

    @Mock
    private ApplicationProperties applicationProperties;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private IngredientGenerator ingredientGenerator;

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
    void onApplicationEvent() {
        assertDoesNotThrow(() -> {

            IngredientGeneratorInputVo ingredientInput = mock(IngredientGeneratorInputVo.class);

            String ingredientName = Generator.generateAString();
            IngredientType ingredientType = Generator.randomEnum(IngredientType.class);

            when(ingredientInput.name()).thenReturn(ingredientName);
            when(ingredientInput.type()).thenReturn(ingredientType);

            when(resourcesProperties.ingredients()).thenReturn(resource);
            when(resource.exists()).thenReturn(true);
            when(resource.getInputStream()).thenReturn(inputStream);

            when(objectMapper.readValue(eq(inputStream), any(TypeReference.class))).thenReturn(List.of(ingredientInput));

            when(ingredientRepository.findByNameAndType(ingredientName, ingredientType)).thenReturn(Optional.empty());


            ingredientGenerator.onApplicationEvent(applicationArguments);


            verify(createIngredientCommand).execute(any(IngredientCreateInputVo.class));

        });
    }

    @Test
    void onApplicationEvent_whenResourceDoesNotExist_thenDontExecute() {

        when(resourcesProperties.ingredients()).thenReturn(resource);
        when(resource.exists()).thenReturn(false);


        ingredientGenerator.onApplicationEvent(applicationArguments);


        verifyNoInteractions(createIngredientCommand, ingredientRepository, objectMapper);

    }

    @Test
    void onApplicationEvent_whenResourceIsNull_thenDontExecute() {

        when(resourcesProperties.ingredients()).thenReturn(null);


        ingredientGenerator.onApplicationEvent(applicationArguments);


        verifyNoInteractions(createIngredientCommand, ingredientRepository, objectMapper);

    }
}
package cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.taco;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.ApplicationProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.start.OnStartProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.start.ResourcesProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.taco.vo.input.*;
import cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.user.vo.input.UserSecurityGeneratorInputVo;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.repository.*;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.*;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.ClientOrderBuilder;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.TacoBuilder;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.factory.ClientOrderBuilderFactory;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.factory.TacoBuilderFactory;
import cmahy.simple.spring.webapp.user.kernel.application.repository.UserSecurityRepository;
import cmahy.simple.spring.webapp.user.kernel.domain.AuthProvider;
import cmahy.simple.spring.webapp.user.kernel.domain.UserSecurityStub;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientOrderGeneratorTest {

    @Mock
    private IngredientRepository<IngredientStub> ingredientRepository;

    @Mock
    private TacoRepository<TacoStub> tacoRepository;

    @Mock
    private ClientOrderRepository<ClientOrderStub> clientOrderRepository;

    @Mock
    private UserSecurityRepository<UserSecurityStub> userSecurityRepository;

    @Mock
    private TacoBuilderFactory<TacoStub> tacoBuilderFactory;

    @Mock
    private ClientOrderBuilderFactory<ClientOrderStub> clientOrderBuilderFactory;

    @Mock
    private ApplicationProperties applicationProperties;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ClientOrderGenerator clientOrderGenerator;

    @Mock(answer = Answers.RETURNS_SELF)
    private TacoBuilder<TacoStub> tacoBuilder;

    @Mock(answer = Answers.RETURNS_SELF)
    private ClientOrderBuilder<ClientOrderStub> clientOrderBuilder;

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

        ClientOrderGeneratorInputVo clientOrderInput = mock(ClientOrderGeneratorInputVo.class);
        TacoGeneratorInputVo tacoInput = mock(TacoGeneratorInputVo.class);
        IngredientGeneratorInputVo ingredientInput = mock(IngredientGeneratorInputVo.class);

        UserSecurityGeneratorInputVo userSecurityInput = mock(UserSecurityGeneratorInputVo.class);

        UserSecurityStub userSecurity = mock(UserSecurityStub.class);
        ClientOrderStub clientOrder = mock(ClientOrderStub.class);
        IngredientStub ingredient = mock(IngredientStub.class);
        TacoStub taco = mock(TacoStub.class);

        String userName = Generator.generateAString();
        AuthProvider authProvider = Generator.randomEnum(AuthProvider.class);

        when(userSecurityInput.userName()).thenReturn(userName);
        when(userSecurityInput.authProvider()).thenReturn(authProvider);

        String ingredientName = Generator.generateAString();
        IngredientType ingredientType = Generator.randomEnum(IngredientType.class);

        when(ingredientInput.name()).thenReturn(ingredientName);
        when(ingredientInput.type()).thenReturn(ingredientType);

        when(resourcesProperties.clientsOrders()).thenReturn(resource);
        when(resource.exists()).thenReturn(true);
        when(resource.getInputStream()).thenReturn(inputStream);

        when(clientOrderInput.tacos()).thenReturn(List.of(tacoInput));
        when(tacoInput.ingredients()).thenReturn(List.of(ingredientInput));
        when(clientOrderInput.user()).thenReturn(userSecurityInput);

        when(objectMapper.readValue(eq(inputStream), any(TypeReference.class))).thenReturn(List.of(clientOrderInput));

        assertDoesNotThrow(() -> {

            when(clientOrderBuilderFactory.create()).thenReturn(clientOrderBuilder);
            when(tacoBuilderFactory.create()).thenReturn(tacoBuilder);

            when(userSecurityRepository.findByUserNameAndAuthProvider(userName, authProvider))
                .thenReturn(Optional.of(userSecurity));
            when(ingredientRepository.findByNameAndType(ingredientName, ingredientType))
                .thenReturn(Optional.of(ingredient));

            when(clientOrderBuilder.build()).thenReturn(clientOrder);
            when(tacoBuilder.build()).thenReturn(taco);

            when(tacoRepository.save(taco)).thenReturn(taco);
            when(clientOrderRepository.save(clientOrder)).thenReturn(clientOrder);


            clientOrderGenerator.onApplicationEvent(applicationArguments);


            verify(clientOrderBuilder).user(userSecurity);
            verify(tacoBuilder).ingredients(List.of(ingredient));
            verify(clientOrder).addTaco(taco);

            verify(tacoRepository).save(taco);
            verify(clientOrderRepository).save(clientOrder);

            verify(ingredientRepository, never()).save(any(IngredientStub.class));
            verify(userSecurityRepository, never()).save(any(UserSecurityStub.class));
        });

    }

    @Test
    void onApplicationEvent_whenResourceDoesNotExist_thenDontExecute() {

        when(resourcesProperties.clientsOrders()).thenReturn(resource);
        when(resource.exists()).thenReturn(false);


        clientOrderGenerator.onApplicationEvent(applicationArguments);


        verifyNoInteractions(
            ingredientRepository,
            tacoRepository,
            clientOrderRepository,
            userSecurityRepository,
            tacoBuilderFactory,
            clientOrderBuilderFactory,
            tacoBuilder,
            clientOrderBuilder,
            objectMapper
        );

    }

    @Test
    void onApplicationEvent_whenResourceIsNull_thenDontExecute() {

        when(resourcesProperties.clientsOrders()).thenReturn(null);


        clientOrderGenerator.onApplicationEvent(applicationArguments);


        verifyNoInteractions(
            ingredientRepository,
            tacoRepository,
            clientOrderRepository,
            userSecurityRepository,
            tacoBuilderFactory,
            clientOrderBuilderFactory,
            tacoBuilder,
            clientOrderBuilder,
            objectMapper
        );

    }
}
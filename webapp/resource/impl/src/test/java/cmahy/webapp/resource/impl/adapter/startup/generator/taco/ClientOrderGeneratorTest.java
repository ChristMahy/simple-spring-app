package cmahy.webapp.resource.impl.adapter.startup.generator.taco;

import cmahy.common.helper.Generator;
import cmahy.webapp.taco.shop.kernel.application.repository.*;
import cmahy.webapp.taco.shop.kernel.domain.*;
import cmahy.webapp.taco.shop.kernel.domain.builder.ClientOrderBuilder;
import cmahy.webapp.taco.shop.kernel.domain.builder.TacoBuilder;
import cmahy.webapp.taco.shop.kernel.domain.builder.factory.ClientOrderBuilderFactory;
import cmahy.webapp.taco.shop.kernel.domain.builder.factory.TacoBuilderFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.ApplicationArguments;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private TacoBuilderFactory<TacoStub> tacoBuilderFactory;

    @Mock
    private ClientOrderBuilderFactory<ClientOrderStub> clientOrderBuilderFactory;

    private ClientOrderGenerator clientOrderGenerator;

    private Integer initialClientOrderSize;

    @Mock(answer = Answers.RETURNS_SELF)
    private TacoBuilder<TacoStub> tacoBuilder;

    @Mock(answer = Answers.RETURNS_SELF)
    private ClientOrderBuilder<ClientOrderStub> clientOrderBuilder;

    @BeforeEach
    void setUp() {
        assertDoesNotThrow(() -> {
            initialClientOrderSize = Generator.randomInt(50, 500);

            clientOrderGenerator = new ClientOrderGenerator(
                ingredientRepository,
                tacoRepository,
                clientOrderRepository,
                tacoBuilderFactory,
                clientOrderBuilderFactory,
                Optional.of(initialClientOrderSize)
            );
        });
    }

    @Test
    void run() {
        assertDoesNotThrow(() -> {
            TacoStub tacoStub = mock(TacoStub.class);
            ClientOrderStub clientOrderStub = mock(ClientOrderStub.class);

            ApplicationArguments applicationArguments = mock(ApplicationArguments.class);

            Arrays.stream(IngredientType.values()).forEach(type -> {
                Set<IngredientStub> ingredients = Stream
                    .generate(() -> new IngredientStub()
                        .setName(Generator.generateAString())
                        .setType(type)
                        .setId(Generator.randomUUID())
                    )
                    .limit(Generator.randomInt(3, 10))
                    .collect(Collectors.toSet());

                when(ingredientRepository.findByType(type)).thenReturn(ingredients);
            });

            when(tacoBuilder.build()).thenReturn(tacoStub);
            when(clientOrderBuilder.build()).thenReturn(clientOrderStub);

            when(tacoBuilderFactory.create()).thenReturn(tacoBuilder);
            when(clientOrderBuilderFactory.create()).thenReturn(clientOrderBuilder);

            when(tacoRepository.save(tacoStub)).thenReturn(tacoStub);
            when(clientOrderRepository.save(clientOrderStub)).thenReturn(clientOrderStub);

            clientOrderGenerator.run(applicationArguments);

            verify(tacoRepository, times(initialClientOrderSize * 2)).save(tacoStub);
            verify(clientOrderRepository, times(initialClientOrderSize)).save(clientOrderStub);
        });
    }
}
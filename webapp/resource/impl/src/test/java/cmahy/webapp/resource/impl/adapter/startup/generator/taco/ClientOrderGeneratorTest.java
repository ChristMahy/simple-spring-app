package cmahy.webapp.resource.impl.adapter.startup.generator.taco;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.application.taco.shop.repository.*;
import cmahy.webapp.resource.impl.domain.taco.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientOrderGeneratorTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private TacoRepository tacoRepository;

    @Mock
    private ClientOrderRepository clientOrderRepository;

    private ClientOrderGenerator clientOrderGenerator;

    private Integer initialClientOrderSize;

    @BeforeEach
    void setUp() {
        assertDoesNotThrow(() -> {
            initialClientOrderSize = Generator.randomInt(50, 500);

            clientOrderGenerator = new ClientOrderGenerator(
                ingredientRepository,
                tacoRepository,
                clientOrderRepository,
                Optional.of(initialClientOrderSize)
            );
        });
    }

    @Test
    void run() {
        assertDoesNotThrow(() -> {
            ApplicationArguments applicationArguments = mock(ApplicationArguments.class);

            Arrays.stream(Ingredient.Type.values()).forEach(type -> {
                Set<Ingredient> ingredients = Stream.generate(() -> {
                        Ingredient ingredient = new Ingredient();

                        ingredient.setName(Generator.generateAString());
                        ingredient.setType(type);
                        ingredient.setId(Generator.generateAString());

                        return ingredient;
                    })
                    .limit(Generator.randomInt(3, 10))
                    .collect(Collectors.toSet());

                when(ingredientRepository.findByType(type)).thenReturn(ingredients);
            });

            when(tacoRepository.save(any(Taco.class))).thenAnswer(invocationOnMock -> Arrays.stream(invocationOnMock.getArguments()).findFirst().orElseThrow());
            when(clientOrderRepository.save(any(ClientOrder.class))).thenAnswer(invocationOnMock -> Arrays.stream(invocationOnMock.getArguments()).findFirst().orElseThrow());

            clientOrderGenerator.run(applicationArguments);

            verify(tacoRepository, times(initialClientOrderSize * 2)).save(any(Taco.class));
            verify(clientOrderRepository, times(initialClientOrderSize)).save(any(ClientOrder.class));
        });
    }
}
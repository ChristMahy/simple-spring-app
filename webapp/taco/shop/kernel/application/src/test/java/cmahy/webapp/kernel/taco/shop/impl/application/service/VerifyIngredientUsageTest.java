package cmahy.webapp.kernel.taco.shop.impl.application.service;

import cmahy.common.helper.Generator;
import cmahy.webapp.taco.shop.kernel.application.repository.IngredientRepository;
import cmahy.webapp.taco.shop.kernel.application.repository.TacoRepository;
import cmahy.webapp.taco.shop.kernel.application.service.VerifyIngredientUsage;
import cmahy.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.webapp.taco.shop.kernel.domain.Taco;
import cmahy.webapp.taco.shop.kernel.domain.id.IngredientId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VerifyIngredientUsageTest {

    @Mock
    private IngredientRepository<Ingredient> ingredientRepository;

    @Mock
    private TacoRepository<Taco> tacoRepository;

    @InjectMocks
    private VerifyIngredientUsage verifyIngredientUsage;

    @Test
    void execute_whenNoUsageFound_thenReturnFalse() {
        assertDoesNotThrow(() -> {
            IngredientId ingredientId = new IngredientId(Generator.generateAString());
            Ingredient ingredient = mock(Ingredient.class);
            Set<Taco> tacos = Collections.emptySet();

            when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.of(ingredient));
            when(tacoRepository.findByIngredientId(ingredientId)).thenReturn(tacos);

            Boolean actual = verifyIngredientUsage.execute(ingredientId);

            assertThat(actual).isFalse();
        });
    }

    @Test
    void execute_whenIngredientNotFound_thenReturnFalse() {
        assertDoesNotThrow(() -> {
            IngredientId ingredientId = new IngredientId(Generator.generateAString());

            when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.empty());

            Boolean actual = verifyIngredientUsage.execute(ingredientId);

            assertThat(actual).isFalse();
        });
    }

    @Test
    void execute_whenIngredientIsUsedByAnyTaco_thenReturnTrue() {
        assertDoesNotThrow(() -> {
            IngredientId ingredientId = new IngredientId(Generator.generateAString());
            Ingredient ingredient = mock(Ingredient.class);
            Set<Taco> tacos = Stream.generate(() -> mock(Taco.class))
                .limit(Generator.randomInt(10, 5000))
                .collect(Collectors.toSet());

            when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.of(ingredient));
            when(tacoRepository.findByIngredientId(ingredientId)).thenReturn(tacos);

            Boolean actual = verifyIngredientUsage.execute(ingredientId);

            assertThat(actual).isTrue();
        });
    }
}
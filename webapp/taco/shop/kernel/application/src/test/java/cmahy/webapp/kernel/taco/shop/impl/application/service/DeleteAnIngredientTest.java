package cmahy.webapp.kernel.taco.shop.impl.application.service;

import cmahy.common.helper.Generator;
import cmahy.webapp.taco.shop.kernel.application.repository.IngredientRepository;
import cmahy.webapp.taco.shop.kernel.application.service.DeleteAnIngredient;
import cmahy.webapp.taco.shop.kernel.application.service.VerifyIngredientUsage;
import cmahy.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.webapp.taco.shop.kernel.domain.id.IngredientId;
import cmahy.webapp.taco.shop.kernel.exception.ingredient.IngredientUsageElementOnDeletionException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteAnIngredientTest {

    @Mock
    private IngredientRepository<Ingredient> ingredientRepository;

    @Mock
    private VerifyIngredientUsage verifyIngredientUsage;

    @InjectMocks
    private DeleteAnIngredient deleteAnIngredient;

    @Test
    void execute() {
        assertDoesNotThrow(() -> {
            IngredientId ingredientId = new IngredientId(Generator.generateAString());

            when(verifyIngredientUsage.execute(ingredientId)).thenReturn(false);

            deleteAnIngredient.execute(ingredientId);

            verify(ingredientRepository).deleteById(ingredientId);
            verifyNoMoreInteractions(ingredientRepository);
        });
    }

    @Test
    void execute_whenUsed_thenThrowsIngredientUsageException() {
        assertThrows(IngredientUsageElementOnDeletionException.class, () -> {
            IngredientId ingredientId = new IngredientId(Generator.generateAString());

            when(verifyIngredientUsage.execute(ingredientId)).thenReturn(true);

            deleteAnIngredient.execute(ingredientId);

            verify(ingredientRepository).deleteById(ingredientId);
        });

        verifyNoInteractions(ingredientRepository);
    }

    @Test
    void execute_whenIdIsNull_shouldBeAcceptedAndProceed() {
        assertDoesNotThrow(() -> {
            deleteAnIngredient.execute(null);

            verifyNoInteractions(ingredientRepository, verifyIngredientUsage);
        });
    }

    @Test
    void execute_whenIdValueIsNull_shouldBeAcceptedAndProceed() {
        assertDoesNotThrow(() -> {
            deleteAnIngredient.execute(new IngredientId(null));

            verifyNoInteractions(ingredientRepository, verifyIngredientUsage);
        });
    }

    @Test
    void execute_whenIdValueIsBlank_shouldBeAcceptedAndProceed() {
        assertDoesNotThrow(() -> {
            deleteAnIngredient.execute(new IngredientId("      \t       "));

            verifyNoInteractions(ingredientRepository, verifyIngredientUsage);
        });
    }
}
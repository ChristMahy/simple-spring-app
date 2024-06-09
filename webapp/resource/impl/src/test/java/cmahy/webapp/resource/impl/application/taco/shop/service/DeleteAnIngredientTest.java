package cmahy.webapp.resource.impl.application.taco.shop.service;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.application.taco.shop.repository.IngredientRepository;
import cmahy.webapp.resource.impl.exception.taco.IngredientUsageElementOnDeletionException;
import cmahy.webapp.resource.taco.shop.id.IngredientId;
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
    private IngredientRepository ingredientRepository;

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

            verify(ingredientRepository).deleteById(ingredientId.value());
            verifyNoMoreInteractions(ingredientRepository);
        });
    }

    @Test
    void execute_whenUsed_thenThrowsIngredientUsageException() {
        assertThrows(IngredientUsageElementOnDeletionException.class, () -> {
            IngredientId ingredientId = new IngredientId(Generator.generateAString());

            when(verifyIngredientUsage.execute(ingredientId)).thenReturn(true);

            deleteAnIngredient.execute(ingredientId);

            verify(ingredientRepository).deleteById(ingredientId.value());
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
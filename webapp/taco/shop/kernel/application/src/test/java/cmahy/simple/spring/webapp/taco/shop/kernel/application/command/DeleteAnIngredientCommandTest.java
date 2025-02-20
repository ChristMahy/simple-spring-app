package cmahy.simple.spring.webapp.taco.shop.kernel.application.command;

import cmahy.simple.spring.webapp.taco.shop.kernel.application.command.DeleteAnIngredientCommand;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.service.DeleteAnIngredient;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.id.IngredientId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteAnIngredientCommandTest {

    @Mock
    private DeleteAnIngredient deleteAnIngredient;

    @InjectMocks
    private DeleteAnIngredientCommand deleteAnIngredientCommand;

    @Test
    void execute() {
        assertDoesNotThrow(() -> {
            IngredientId id = mock(IngredientId.class);

            deleteAnIngredientCommand.execute(id);

            verify(deleteAnIngredient).execute(id);
        });
    }

    @Test
    void execute_whenIdIsNull_thenShouldBeAcceptedAndProceed() {
        assertDoesNotThrow(() -> {
            deleteAnIngredientCommand.execute(null);

            verify(deleteAnIngredient).execute(null);
        });
    }
}
package cmahy.webapp.resource.impl.application.taco.shop.command;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.application.taco.shop.repository.IngredientRepository;
import cmahy.webapp.resource.impl.application.taco.shop.service.DeleteAnIngredient;
import cmahy.webapp.resource.impl.domain.taco.id.IngredientId;
import cmahy.webapp.resource.impl.exception.taco.IngredientNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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
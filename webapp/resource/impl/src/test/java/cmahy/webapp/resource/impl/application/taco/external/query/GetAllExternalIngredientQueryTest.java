package cmahy.webapp.resource.impl.application.taco.external.query;

import cmahy.webapp.resource.impl.application.taco.external.repository.IngredientExternalRepository;
import cmahy.webapp.resource.impl.domain.taco.external.page.IngredientExternalPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllExternalIngredientQueryTest {

    @Mock
    private IngredientExternalRepository ingredientExternalRepository;

    @InjectMocks
    private GetAllExternalIngredientQuery getAllExternalIngredientQuery;

    @Test
    void execute() {
        assertDoesNotThrow(() -> {
            IngredientExternalPage page = mock(IngredientExternalPage.class);

            when(ingredientExternalRepository.findAllIngredients()).thenReturn(page);

            IngredientExternalPage actual = getAllExternalIngredientQuery.execute();

            assertThat(actual)
                .isNotNull()
                .isEqualTo(page);
        });
    }

    @Test
    void execute_whenExceptionIsThrown_thenPropagate() {
        assertThrows(Throwable.class, () -> {
            when(ingredientExternalRepository.findAllIngredients()).thenThrow(Throwable.class);

            getAllExternalIngredientQuery.execute();
        });
    }
}
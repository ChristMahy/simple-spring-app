package cmahy.webapp.resource.impl.adapter.startup.generator.taco;

import cmahy.webapp.resource.impl.adapter.taco.shop.repository.IngredientRepositoryImpl;
import cmahy.webapp.resource.impl.application.taco.shop.repository.IngredientRepository;
import cmahy.webapp.resource.impl.domain.taco.Ingredient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.ApplicationArguments;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientGeneratorTest {

    @Mock
    private IngredientRepositoryImpl ingredientRepository;

    @InjectMocks
    private IngredientGenerator ingredientGenerator;

    @Test
    void run() {
        assertDoesNotThrow(() -> {
            ApplicationArguments applicationArguments = mock(ApplicationArguments.class);

            ingredientGenerator.run(applicationArguments);

            verify(ingredientRepository, times(10)).save(any(Ingredient.class));
        });
    }
}
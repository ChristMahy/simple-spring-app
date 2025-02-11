package cmahy.webapp.resource.impl.adapter.startup.generator.taco;

import cmahy.webapp.taco.shop.kernel.application.command.CreateIngredientCommand;
import cmahy.webapp.taco.shop.kernel.application.repository.IngredientRepository;
import cmahy.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.webapp.taco.shop.kernel.domain.IngredientType;
import cmahy.webapp.taco.shop.kernel.vo.input.IngredientCreateInputVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.ApplicationArguments;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientGeneratorTest {

    @Mock
    private CreateIngredientCommand createIngredientCommand;

    @Mock
    private IngredientRepository<? extends Ingredient> ingredientRepository;

    @InjectMocks
    private IngredientGenerator ingredientGenerator;

    @Test
    void run() {
        assertDoesNotThrow(() -> {
            ApplicationArguments applicationArguments = mock(ApplicationArguments.class);

            when(ingredientRepository.findByNameAndType(anyString(), any(IngredientType.class))).thenReturn(Optional.empty());

            ingredientGenerator.run(applicationArguments);

            verify(createIngredientCommand, times(10)).execute(any(IngredientCreateInputVo.class));
        });
    }
}
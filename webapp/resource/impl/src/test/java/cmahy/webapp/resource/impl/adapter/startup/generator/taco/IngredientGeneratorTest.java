package cmahy.webapp.resource.impl.adapter.startup.generator.taco;

import cmahy.webapp.resource.impl.application.taco.shop.command.CreateIngredientCommand;
import cmahy.webapp.resource.impl.application.taco.shop.vo.input.IngredientCreateInputAppVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.ApplicationArguments;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientGeneratorTest {

    @Mock
    private CreateIngredientCommand createIngredientCommand;

    @InjectMocks
    private IngredientGenerator ingredientGenerator;

    @Test
    void run() {
        assertDoesNotThrow(() -> {
            ApplicationArguments applicationArguments = mock(ApplicationArguments.class);

            ingredientGenerator.run(applicationArguments);

            verify(createIngredientCommand, times(10)).execute(any(IngredientCreateInputAppVo.class));
        });
    }
}
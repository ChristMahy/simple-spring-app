package cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.taco;

import cmahy.simple.spring.webapp.taco.shop.kernel.application.command.CreateIngredientCommand;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.repository.IngredientRepository;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.IngredientType;
import cmahy.simple.spring.webapp.taco.shop.kernel.vo.input.IngredientCreateInputVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.context.event.ApplicationStartedEvent;

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
    void onApplicationEvent() {
        assertDoesNotThrow(() -> {
            ApplicationStartedEvent applicationArguments = mock(ApplicationStartedEvent.class);

            when(ingredientRepository.findByNameAndType(anyString(), any(IngredientType.class))).thenReturn(Optional.empty());

            ingredientGenerator.onApplicationEvent(applicationArguments);

            verify(createIngredientCommand, times(10)).execute(any(IngredientCreateInputVo.class));
        });
    }
}
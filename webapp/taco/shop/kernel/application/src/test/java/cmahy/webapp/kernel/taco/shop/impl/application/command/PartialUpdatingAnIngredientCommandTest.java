package cmahy.webapp.kernel.taco.shop.impl.application.command;

import cmahy.webapp.taco.shop.kernel.application.command.PartialUpdatingAnIngredientCommand;
import cmahy.webapp.taco.shop.kernel.application.service.PartialUpdatingAnIngredient;
import cmahy.webapp.taco.shop.kernel.domain.id.IngredientId;
import cmahy.webapp.taco.shop.kernel.vo.input.IngredientUpdateInputVo;
import cmahy.webapp.taco.shop.kernel.vo.output.IngredientOutputVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PartialUpdatingAnIngredientCommandTest {

    @Mock
    private PartialUpdatingAnIngredient partialUpdatingAnIngredient;

    @InjectMocks
    private PartialUpdatingAnIngredientCommand partialUpdatingAnIngredientCommand;

    @Test
    void execute() {
        assertDoesNotThrow(() -> {
            IngredientId ingredientId = mock(IngredientId.class);
            IngredientUpdateInputVo ingredientUpdateInputVo = mock(IngredientUpdateInputVo.class);

            IngredientOutputVo ingredientOutputVo = mock(IngredientOutputVo.class);

            when(partialUpdatingAnIngredient.execute(ingredientId, ingredientUpdateInputVo)).thenReturn(ingredientOutputVo);

            IngredientOutputVo actual = partialUpdatingAnIngredientCommand.execute(ingredientId, ingredientUpdateInputVo);

            assertThat(actual)
                .isNotNull()
                .isEqualTo(ingredientOutputVo);
        });
    }
}
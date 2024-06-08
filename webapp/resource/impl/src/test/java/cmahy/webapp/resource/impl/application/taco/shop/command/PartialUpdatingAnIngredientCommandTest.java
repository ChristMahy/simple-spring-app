package cmahy.webapp.resource.impl.application.taco.shop.command;

import cmahy.webapp.resource.impl.application.taco.shop.service.PartialUpdatingAnIngredient;
import cmahy.webapp.resource.impl.application.taco.shop.vo.input.IngredientUpdateInputAppVo;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.IngredientOutputAppVo;
import cmahy.webapp.resource.impl.domain.taco.id.IngredientId;
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
            IngredientUpdateInputAppVo ingredientUpdateInputAppVo = mock(IngredientUpdateInputAppVo.class);

            IngredientOutputAppVo ingredientOutputAppVo = mock(IngredientOutputAppVo.class);

            when(partialUpdatingAnIngredient.execute(ingredientId, ingredientUpdateInputAppVo)).thenReturn(ingredientOutputAppVo);

            IngredientOutputAppVo actual = partialUpdatingAnIngredientCommand.execute(ingredientId, ingredientUpdateInputAppVo);

            assertThat(actual)
                .isNotNull()
                .isEqualTo(ingredientOutputAppVo);
        });
    }
}
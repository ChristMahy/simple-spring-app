package cmahy.webapp.resource.impl.application.taco.shop.command;

import cmahy.webapp.resource.impl.application.taco.shop.service.CreateAnIngredient;
import cmahy.webapp.resource.impl.application.taco.shop.vo.input.IngredientCreateInputAppVo;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.IngredientOutputAppVo;
import cmahy.webapp.resource.impl.helper.ValidatorHelper;
import cmahy.webapp.resource.impl.helper.ValidatorHelperExtension;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static cmahy.webapp.resource.impl.application.taco.shop.command.CreateIngredientCommand.I18N_KEY_CREATE_INGREDIENT_NOT_NULL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith({ValidatorHelperExtension.class, MockitoExtension.class})
class CreateIngredientCommandTest {

    @Mock
    private CreateAnIngredient createAnIngredient;

    @InjectMocks
    private CreateIngredientCommand createIngredientCommand;

    @Test
    void execute() {
        assertDoesNotThrow(() -> {
            IngredientCreateInputAppVo createInputAppVo = mock(IngredientCreateInputAppVo.class);
            IngredientOutputAppVo outputAppVo = mock(IngredientOutputAppVo.class);

            when(createAnIngredient.execute(createInputAppVo)).thenReturn(outputAppVo);

            IngredientOutputAppVo actual = createIngredientCommand.execute(createInputAppVo);

            assertThat(actual).isEqualTo(outputAppVo);
        });
    }

    @Test
    void execute_onNullGivenAsValue_then(ValidatorHelper validatorHelper) {
        assertDoesNotThrow(() -> {
            Set<ConstraintViolation<CreateIngredientCommand>> constraintViolations = validatorHelper.validateMethod(
                createIngredientCommand,
                "execute",
                new Object[]{null}
            );

            assertThat(
                constraintViolations.stream()
                    .map(ConstraintViolation::getMessage)
                    .toList()
            ).containsExactlyInAnyOrder(I18N_KEY_CREATE_INGREDIENT_NOT_NULL);

            verifyNoInteractions(createAnIngredient);
        });
    }
}
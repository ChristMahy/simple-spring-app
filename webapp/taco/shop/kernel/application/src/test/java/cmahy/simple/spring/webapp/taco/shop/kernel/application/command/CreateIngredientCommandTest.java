package cmahy.simple.spring.webapp.taco.shop.kernel.application.command;

import cmahy.simple.spring.webapp.taco.shop.kernel.application.service.CreateAnIngredient;
import cmahy.simple.spring.webapp.taco.shop.kernel.helper.ValidatorHelper;
import cmahy.simple.spring.webapp.taco.shop.kernel.helper.ValidatorHelperExtension;
import cmahy.simple.spring.webapp.taco.shop.kernel.vo.input.IngredientCreateInputVo;
import cmahy.simple.spring.webapp.taco.shop.kernel.vo.output.IngredientOutputVo;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static cmahy.simple.spring.webapp.taco.shop.kernel.application.command.CreateIngredientCommand.I18N_KEY_CREATE_INGREDIENT_NOT_NULL;
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
            IngredientCreateInputVo createInputVo = mock(IngredientCreateInputVo.class);
            IngredientOutputVo outputVo = mock(IngredientOutputVo.class);

            when(createAnIngredient.execute(createInputVo)).thenReturn(outputVo);

            IngredientOutputVo actual = createIngredientCommand.execute(createInputVo);

            assertThat(actual).isEqualTo(outputVo);
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
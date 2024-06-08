package cmahy.webapp.resource.impl.application.taco.shop.service;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.application.taco.shop.vo.input.IngredientCreateInputAppVo;
import cmahy.webapp.resource.impl.domain.taco.Ingredient;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.impl.helper.ValidatorHelper;
import cmahy.webapp.resource.impl.helper.ValidatorHelperExtension;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static cmahy.webapp.resource.impl.domain.taco.Ingredient.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith({ValidatorHelperExtension.class, MockitoExtension.class})
class IngredientFactoryTest {

    @InjectMocks
    private IngredientFactory ingredientFactory;

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            IngredientCreateInputAppVo createInputAppVo = new IngredientCreateInputAppVo(
                Generator.generateAStringWithoutSpecialChars(),
                Generator.randomEnum(Ingredient.Type.class).name()
            );

            Ingredient actual = ingredientFactory.create(createInputAppVo);

            assertThat(actual).isNotNull();

            assertThat(actual.getId()).isNull();
            assertThat(actual.getName()).isEqualTo(createInputAppVo.name());
            assertThat(actual.getType().name()).isEqualTo(createInputAppVo.type());
        });
    }

    @Test
    void execute_whenValuesHaveSpaceBeforeAndAfter_thenTrimSpaceAndCreateIngredient() {
        assertDoesNotThrow(() -> {
            String name = Generator.generateAStringWithoutSpecialChars();
            Type type = Generator.randomEnum(Ingredient.Type.class);

            IngredientCreateInputAppVo createInputAppVo = new IngredientCreateInputAppVo(
                "         " + name + "              ",
                "            " + type + "                 "
            );

            Ingredient actual = ingredientFactory.create(createInputAppVo);

            assertThat(actual).isNotNull();

            assertThat(actual.getId()).isNull();
            assertThat(actual.getName()).isEqualTo(name.trim());
            assertThat(actual.getType()).isEqualTo(type);
        });
    }

    @Test
    void execute_onEnumValueDoesNotExist_thenThrowAValidationException(ValidatorHelper validatorHelper) {
        assertDoesNotThrow(() -> {
            IngredientCreateInputAppVo createInputAppVo = new IngredientCreateInputAppVo(
                Generator.generateAStringWithoutSpecialChars(),
                Generator.generateAStringWithoutSpecialChars(100)
            );

            Ingredient actual = ingredientFactory.create(createInputAppVo);

            Set<ConstraintViolation<IngredientFactory>> constraintViolations =
                validatorHelper.validateReturnValue(ingredientFactory, "create", List.of(IngredientCreateInputAppVo.class), actual);

            assertThat(
                constraintViolations.stream()
                    .map(ConstraintViolation::getMessage)
                    .toList()
            ).containsExactlyInAnyOrder(I18N_KEY_TYPE_NOT_NULL);
        });
    }

    @Test
    void execute_onAllFieldsAreNull_thenThrowAValidationException(ValidatorHelper validatorHelper) {
        assertDoesNotThrow(() -> {
            IngredientCreateInputAppVo createInputAppVo = new IngredientCreateInputAppVo(
                null,
                null
            );

            Ingredient actual = ingredientFactory.create(createInputAppVo);

            Set<ConstraintViolation<IngredientFactory>> constraintViolations =
                validatorHelper.validateReturnValue(ingredientFactory, "create", List.of(IngredientCreateInputAppVo.class), actual);

            assertThat(
                constraintViolations.stream()
                    .map(ConstraintViolation::getMessage)
                    .toList()
            ).containsExactlyInAnyOrder(
                I18N_KEY_NAME_NOT_NULL,
                I18N_KEY_NAME_NOT_BLANK,
                I18N_KEY_TYPE_NOT_NULL
            );
        });
    }

    @Test
    void execute_onAllBlankFields_thenThrowAValidationException(ValidatorHelper validatorHelper) {
        assertDoesNotThrow(() -> {
            IngredientCreateInputAppVo createInputAppVo = new IngredientCreateInputAppVo(
                "                   \t      ",
                "                   \t    "
            );

            Ingredient actual = ingredientFactory.create(createInputAppVo);

            Set<ConstraintViolation<IngredientFactory>> constraintViolations =
                validatorHelper.validateReturnValue(ingredientFactory, "create", List.of(IngredientCreateInputAppVo.class), actual);

            assertThat(
                constraintViolations.stream()
                    .map(ConstraintViolation::getMessage)
                    .toList()
            ).containsExactlyInAnyOrder(
                I18N_KEY_NAME_NOT_BLANK,
                I18N_KEY_TYPE_NOT_NULL
            );
        });
    }

    @Test
    void create_whenNullGivenAsInput_thenThrowNullException() {
        assertThrows(NullException.class, () -> {
            ingredientFactory.create(null);
        });
    }
}
package cmahy.webapp.kernel.taco.shop.impl.application.service;

import cmahy.common.helper.Generator;
import cmahy.webapp.kernel.taco.shop.impl.helper.ValidatorHelper;
import cmahy.webapp.kernel.taco.shop.impl.helper.ValidatorHelperExtension;
import cmahy.webapp.taco.shop.kernel.application.service.IngredientFactory;
import cmahy.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.webapp.taco.shop.kernel.domain.IngredientType;
import cmahy.webapp.taco.shop.kernel.domain.builder.IngredientBuilderStub;
import cmahy.webapp.taco.shop.kernel.domain.builder.factory.IngredientBuilderFactory;
import cmahy.webapp.taco.shop.kernel.exception.RequiredException;
import cmahy.webapp.taco.shop.kernel.vo.input.IngredientCreateInputVo;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static cmahy.webapp.taco.shop.kernel.domain.Ingredient.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith({ValidatorHelperExtension.class, MockitoExtension.class})
class IngredientFactoryTest {

    @Mock
    private IngredientBuilderFactory<? extends Ingredient> ingredientBuilderFactory;

    @InjectMocks
    private IngredientFactory ingredientFactory;

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            IngredientType ingredientType = Generator.randomEnum(IngredientType.class);
            IngredientCreateInputVo createInputVo = new IngredientCreateInputVo(
                Generator.generateAStringWithoutSpecialChars(),
                ingredientType.name()
            );

            when(ingredientBuilderFactory.create()).thenAnswer(_ -> new IngredientBuilderStub());

            Ingredient actual = ingredientFactory.create(createInputVo);

            assertThat(actual).isNotNull();

            assertThat(actual.getId()).containsPattern("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}");
            assertThat(actual.getName()).isEqualTo(createInputVo.name());
            assertThat(actual.getType().name()).isEqualTo(createInputVo.type());
        });
    }

    @Test
    void execute_whenValuesHaveSpaceBeforeAndAfter_thenTrimSpaceAndCreateIngredient() {
        assertDoesNotThrow(() -> {
            String name = Generator.generateAStringWithoutSpecialChars();
            IngredientType type = Generator.randomEnum(IngredientType.class);

            IngredientCreateInputVo createInputVo = new IngredientCreateInputVo(
                "         " + name + "              ",
                "            " + type.name() + "                 "
            );

            when(ingredientBuilderFactory.create()).thenAnswer(_ -> new IngredientBuilderStub());

            Ingredient actual = ingredientFactory.create(createInputVo);

            assertThat(actual).isNotNull();

            assertThat(actual.getId()).containsPattern("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}");
            assertThat(actual.getName()).isEqualTo(name.trim());
            assertThat(actual.getType()).isEqualTo(type);
        });
    }

    @Test
    void execute_onEnumValueDoesNotExist_thenThrowAValidationException(ValidatorHelper validatorHelper) {
        assertDoesNotThrow(() -> {
            IngredientCreateInputVo createInputVo = new IngredientCreateInputVo(
                Generator.generateAStringWithoutSpecialChars(),
                Generator.generateAStringWithoutSpecialChars(100)
            );

            when(ingredientBuilderFactory.create()).thenAnswer(_ -> new IngredientBuilderStub());

            Ingredient actual = ingredientFactory.create(createInputVo);
            Set<ConstraintViolation<IngredientFactory>> constraintViolations =
                validatorHelper.validateReturnValue(ingredientFactory, "create", List.of(IngredientCreateInputVo.class), actual);

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
            IngredientCreateInputVo createInputVo = new IngredientCreateInputVo(
                null,
                null
            );

            when(ingredientBuilderFactory.create()).thenAnswer(_ -> new IngredientBuilderStub());

            Ingredient actual = ingredientFactory.create(createInputVo);

            Set<ConstraintViolation<IngredientFactory>> constraintViolations =
                validatorHelper.validateReturnValue(ingredientFactory, "create", List.of(IngredientCreateInputVo.class), actual);

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
            IngredientCreateInputVo createInputVo = new IngredientCreateInputVo(
                "                   \t      ",
                "                   \t    "
            );

            when(ingredientBuilderFactory.create()).thenAnswer(_ -> new IngredientBuilderStub());

            Ingredient actual = ingredientFactory.create(createInputVo);

            Set<ConstraintViolation<IngredientFactory>> constraintViolations =
                validatorHelper.validateReturnValue(ingredientFactory, "create", List.of(IngredientCreateInputVo.class), actual);

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
        assertThrows(RequiredException.class, () -> {
            ingredientFactory.create(null);
        });
    }
}
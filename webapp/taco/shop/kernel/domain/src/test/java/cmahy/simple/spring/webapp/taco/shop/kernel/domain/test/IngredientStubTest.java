package cmahy.simple.spring.webapp.taco.shop.kernel.domain.test;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.*;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class IngredientStubTest {

    @Test
    void ingredientNewInstance_whenAllFieldsComplete_thenFieldsReturnValue() {
        assertDoesNotThrow(() -> {
            UUID id = Generator.randomUUID();
            String name = Generator.generateAString();
            IngredientType type = Generator.randomEnum(IngredientType.class);

            Ingredient actual = new IngredientStub()
                .setId(id)
                .setName(name)
                .setType(type);

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(IngredientStub.class);

            assertThat(actual.getId()).isEqualTo(id);
            assertThat(actual.getName()).isEqualTo(name);
            assertThat(actual.getType()).isEqualTo(type);
        });
    }

    @Test
    void ingredientNewInstance_whenAllFieldsEmpty_thenFieldsReturnNull() {
        assertDoesNotThrow(() -> {
            Ingredient actual = new IngredientStub();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(IngredientStub.class);

            assertThat(actual.getId()).isNull();
            assertThat(actual.getName()).isNull();
            assertThat(actual.getType()).isNull();
        });
    }

    @Test
    void ingredientNewInstance_whenUseSetterToOverrideValueToNull_thenFieldsReturnNull() {
        assertDoesNotThrow(() -> {
            UUID id = Generator.randomUUID();
            String name = Generator.generateAString();
            IngredientType type = Generator.randomEnum(IngredientType.class);

            IngredientStub actual = new IngredientStub()
                .setId(id)
                .setName(name)
                .setType(type);

            actual
                .setId(null)
                .setName(null)
                .setType(null);

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(IngredientStub.class);

            assertThat(actual.getId()).isNull();
            assertThat(actual.getName()).isNull();
            assertThat(actual.getType()).isNull();
        });
    }
}
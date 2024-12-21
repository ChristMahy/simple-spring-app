package cmahy.webapp.taco.shop.kernel.domain.builder.test;

import cmahy.common.helper.Generator;
import cmahy.webapp.taco.shop.kernel.domain.*;
import cmahy.webapp.taco.shop.kernel.domain.builder.IngredientBuilderStub;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class IngredientBuilderStubTest {

    @Test
    void build() {
        assertDoesNotThrow(() -> {
            String id = Generator.generateAString();
            String name = Generator.generateAString();
            IngredientType type = Generator.randomEnum(IngredientType.class);

            Ingredient actual = new IngredientBuilderStub()
                .id(id)
                .name(name)
                .type(type)
                .build();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(IngredientStub.class);

            assertThat(actual.getId()).isEqualTo(id);
            assertThat(actual.getName()).isEqualTo(name);
            assertThat(actual.getType()).isEqualTo(type);
        });
    }

    @Test
    void buildWithOriginal_thenReturnOriginalWithModifiedValuesAndKeepingSameId() {
        assertDoesNotThrow(() -> {
            String name = Generator.generateAString();
            IngredientType type = Generator.randomEnum(IngredientType.class);

            IngredientStub original = new IngredientStub()
                .setId(Generator.generateAString(30))
                .setName(Generator.generateAString(30))
                .setType(Generator.randomEnum(IngredientType.class));

            Ingredient actual = new IngredientBuilderStub(original)
                .name(name)
                .type(type)
                .build();

            assertThat(actual)
                .isNotNull()
                .isSameAs(original);

            assertThat(actual.getId()).isEqualTo(original.getId());
            assertThat(actual.getName()).isEqualTo(name);
            assertThat(actual.getType()).isEqualTo(type);
        });
    }

    @Test
    void buildWithNullAsOriginal_thenBuildNewOne() {
        assertDoesNotThrow(() -> {
            String id = Generator.generateAString();
            String name = Generator.generateAString();
            IngredientType type = Generator.randomEnum(IngredientType.class);

            Ingredient actual = new IngredientBuilderStub(null)
                .id(id)
                .name(name)
                .type(type)
                .build();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(IngredientStub.class);

            assertThat(actual.getId()).isEqualTo(id);
            assertThat(actual.getName()).isEqualTo(name);
            assertThat(actual.getType()).isEqualTo(type);
        });
    }
}
package cmahy.webapp.taco.shop.adapter.webclient.entity.builder;

import cmahy.common.helper.Generator;
import cmahy.webapp.taco.shop.adapter.webclient.entity.domain.ExternalIngredient;
import cmahy.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.webapp.taco.shop.kernel.domain.IngredientType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ExternalIngredientBuilderTest {
    
    @Test
    void build() {
        assertDoesNotThrow(() -> {
            String name = Generator.generateAString();
            IngredientType type = Generator.randomEnum(IngredientType.class);

            Ingredient actual = new ExternalIngredientBuilder()
                .name(name)
                .type(type)
                .build();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(ExternalIngredient.class);

            assertThat(actual.getId()).isNull();
            assertThat(actual.getName()).isEqualTo(name);
            assertThat(actual.getType()).isEqualTo(type);
        });
    }

    @Test
    void buildWithOriginal_thenReturnOriginalWithModifiedValuesAndKeepingSameId() {
        assertDoesNotThrow(() -> {
            String name = Generator.generateAString();
            IngredientType type = Generator.randomEnum(IngredientType.class);

            ExternalIngredient original = new ExternalIngredient()
                .setId(Generator.randomUUID())
                .setName(Generator.generateAString(30))
                .setType(Generator.randomEnum(IngredientType.class));

            Ingredient actual = new ExternalIngredientBuilder(original)
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
            String name = Generator.generateAString();
            IngredientType type = Generator.randomEnum(IngredientType.class);

            Ingredient actual = new ExternalIngredientBuilder(null)
                .name(name)
                .type(type)
                .build();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(ExternalIngredient.class);

            assertThat(actual.getId()).isNull();
            assertThat(actual.getName()).isEqualTo(name);
            assertThat(actual.getType()).isEqualTo(type);
        });
    }
}
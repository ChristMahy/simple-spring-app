package cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.test;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.*;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.TacoBuilderStub;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

class TacoBuilderStubTest {

    @Test
    void build() {
        assertDoesNotThrow(() -> {
            Date createdAt = new Date();
            String name = Generator.generateAString();
            Collection<IngredientStub> ingredients = Stream
                .generate(() -> mock(IngredientStub.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            Taco actual = new TacoBuilderStub()
                .createdAt(createdAt)
                .name(name)
                .ingredients(ingredients)
                .build();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(TacoStub.class);

            assertThat(actual.getId()).isNull();
            assertThat(actual.getName()).isEqualTo(name);
            assertThat(actual.getCreatedAt()).isEqualTo(createdAt);
            assertThat(actual.getIngredients()).containsExactlyElementsOf(ingredients);
        });
    }

    @Test
    void buildWithOriginal_thenReturnOriginalWithModifiedValuesAndKeepingSameId() {
        assertDoesNotThrow(() -> {
            Date createdAt = new Date();
            String name = Generator.generateAString();
            Collection<IngredientStub> ingredients = Stream
                .generate(() -> mock(IngredientStub.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            TacoStub original = new TacoStub()
                .setId(Generator.randomUUID())
                .setName(Generator.generateAString(30))
                .setCreatedAt(new Date())
                .setIngredients(
                    Stream.generate(() -> mock(IngredientStub.class))
                        .limit(30)
                        .toList()
                );

            Taco actual = new TacoBuilderStub(original)
                .createdAt(createdAt)
                .name(name)
                .ingredients(ingredients)
                .build();

            assertThat(actual)
                .isNotNull()
                .isSameAs(original);

            assertThat(actual.getId()).isEqualTo(original.getId());
            assertThat(actual.getName()).isEqualTo(name);
            assertThat(actual.getCreatedAt()).isEqualTo(createdAt);
            assertThat(actual.getIngredients()).containsExactlyElementsOf(ingredients);
        });
    }

    @Test
    void buildWithOriginal_thenReturnOriginalWithoutModifiedValuesAndKeepingSameValues() {
        assertDoesNotThrow(() -> {

            TacoStub original = new TacoStub()
                .setId(Generator.randomUUID())
                .setName(Generator.generateAString(30))
                .setCreatedAt(new Date())
                .setIngredients(
                    Stream.generate(() -> mock(IngredientStub.class))
                        .limit(30)
                        .toList()
                );

            Taco actual = new TacoBuilderStub(original).build();

            assertThat(actual)
                .isNotNull()
                .isSameAs(original);

            assertThat(actual.getId()).isEqualTo(original.getId());
            assertThat(actual.getName()).isEqualTo(original.getName());
            assertThat(actual.getCreatedAt()).isEqualTo(original.getCreatedAt());
            assertThat(actual.getIngredients()).containsExactlyElementsOf(original.getIngredients());
        });
    }

    @Test
    void buildWithNullAsOriginal_thenBuildNewOne() {
        assertDoesNotThrow(() -> {
            Date createdAt = new Date();
            String name = Generator.generateAString();
            Collection<IngredientStub> ingredients = Stream
                .generate(() -> mock(IngredientStub.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            Taco actual = new TacoBuilderStub(null)
                .createdAt(createdAt)
                .name(name)
                .ingredients(ingredients)
                .build();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(TacoStub.class);

            assertThat(actual.getId()).isNull();
            assertThat(actual.getName()).isEqualTo(name);
            assertThat(actual.getCreatedAt()).isEqualTo(createdAt);
            assertThat(actual.getIngredients()).containsExactlyElementsOf(ingredients);
        });
    }
}
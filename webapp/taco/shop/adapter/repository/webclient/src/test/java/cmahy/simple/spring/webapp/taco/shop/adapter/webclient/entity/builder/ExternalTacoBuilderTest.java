package cmahy.simple.spring.webapp.taco.shop.adapter.webclient.entity.builder;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.taco.shop.adapter.webclient.entity.domain.ExternalIngredient;
import cmahy.simple.spring.webapp.taco.shop.adapter.webclient.entity.domain.ExternalTaco;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Taco;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

class ExternalTacoBuilderTest {

    @Test
    void build() {
        assertDoesNotThrow(() -> {
            Date createdAt = new Date();
            String name = Generator.generateAString();
            Collection<ExternalIngredient> ingredients = Stream
                .generate(() -> mock(ExternalIngredient.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            Taco actual = new ExternalTacoBuilder()
                .createdAt(createdAt)
                .name(name)
                .ingredients(ingredients)
                .build();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(ExternalTaco.class);

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
            Collection<ExternalIngredient> ingredients = Stream
                .generate(() -> mock(ExternalIngredient.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            ExternalTaco original = new ExternalTaco()
                .setId(Generator.randomUUID())
                .setName(Generator.generateAString(30))
                .setCreatedAt(new Date())
                .setIngredients(
                    Stream.generate(() -> mock(ExternalIngredient.class))
                        .limit(30)
                        .toList()
                );

            Taco actual = new ExternalTacoBuilder(original)
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

            ExternalTaco original = new ExternalTaco()
                .setId(Generator.randomUUID())
                .setName(Generator.generateAString(30))
                .setCreatedAt(new Date())
                .setIngredients(
                    Stream.generate(() -> mock(ExternalIngredient.class))
                        .limit(30)
                        .toList()
                );

            Taco actual = new ExternalTacoBuilder(original).build();

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
            Collection<ExternalIngredient> ingredients = Stream
                .generate(() -> mock(ExternalIngredient.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            Taco actual = new ExternalTacoBuilder(null)
                .createdAt(createdAt)
                .name(name)
                .ingredients(ingredients)
                .build();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(ExternalTaco.class);

            assertThat(actual.getId()).isNull();
            assertThat(actual.getName()).isEqualTo(name);
            assertThat(actual.getCreatedAt()).isEqualTo(createdAt);
            assertThat(actual.getIngredients()).containsExactlyElementsOf(ingredients);
        });
    }
}
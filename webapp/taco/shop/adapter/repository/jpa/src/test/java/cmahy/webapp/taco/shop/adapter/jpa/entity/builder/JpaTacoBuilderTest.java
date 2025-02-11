package cmahy.webapp.taco.shop.adapter.jpa.entity.builder;

import cmahy.common.helper.Generator;
import cmahy.webapp.taco.shop.adapter.jpa.entity.domain.JpaIngredient;
import cmahy.webapp.taco.shop.adapter.jpa.entity.domain.JpaTaco;
import cmahy.webapp.taco.shop.kernel.domain.Taco;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

class JpaTacoBuilderTest {

    @Test
    void build() {
        assertDoesNotThrow(() -> {
            Date createdAt = new Date();
            String name = Generator.generateAString();
            Collection<JpaIngredient> ingredients = Stream
                .generate(() -> mock(JpaIngredient.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            Taco actual = new JpaTacoBuilder()
                .createdAt(createdAt)
                .name(name)
                .ingredients(ingredients)
                .build();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(JpaTaco.class);

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
            Collection<JpaIngredient> ingredients = Stream
                .generate(() -> mock(JpaIngredient.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            JpaTaco original = new JpaTaco()
                .setId(Generator.randomUUID())
                .setName(Generator.generateAString(30))
                .setCreatedAt(new Date())
                .setIngredients(
                    Stream.generate(() -> mock(JpaIngredient.class))
                        .limit(30)
                        .toList()
                );

            Taco actual = new JpaTacoBuilder(original)
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
    void buildWithNullAsOriginal_thenBuildNewOne() {
        assertDoesNotThrow(() -> {
            Date createdAt = new Date();
            String name = Generator.generateAString();
            Collection<JpaIngredient> ingredients = Stream
                .generate(() -> mock(JpaIngredient.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            Taco actual = new JpaTacoBuilder(null)
                .createdAt(createdAt)
                .name(name)
                .ingredients(ingredients)
                .build();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(JpaTaco.class);

            assertThat(actual.getId()).isNull();
            assertThat(actual.getName()).isEqualTo(name);
            assertThat(actual.getCreatedAt()).isEqualTo(createdAt);
            assertThat(actual.getIngredients()).containsExactlyElementsOf(ingredients);
        });
    }
}
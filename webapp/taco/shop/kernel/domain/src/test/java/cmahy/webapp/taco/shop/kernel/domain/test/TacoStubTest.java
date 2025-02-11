package cmahy.webapp.taco.shop.kernel.domain.test;

import cmahy.common.helper.Generator;
import cmahy.webapp.taco.shop.kernel.domain.*;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

class TacoStubTest {

    @Test
    void tacoNewInstance_whenAllFieldsComplete_thenFieldsReturnValue() {
        assertDoesNotThrow(() -> {
            UUID id = Generator.randomUUID();
            Date createdAt = new Date();
            String name = Generator.generateAString();
            Collection<IngredientStub> ingredients = Stream
                .generate(() -> mock(IngredientStub.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            Taco actual = new TacoStub()
                .setId(id)
                .setCreatedAt(createdAt)
                .setName(name)
                .setIngredients(ingredients);

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(TacoStub.class);

            assertThat(actual.getId()).isEqualTo(id);
            assertThat(actual.getName()).isEqualTo(name);
            assertThat(actual.getCreatedAt()).isEqualTo(createdAt);
            assertThat(actual.getIngredients()).containsExactlyElementsOf(ingredients);
        });
    }

    @Test
    void tacoNewInstance_whenAllFieldsEmpty_thenFieldsReturnNull() {
        assertDoesNotThrow(() -> {
            Taco actual = new TacoStub();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(TacoStub.class);

            assertThat(actual.getId()).isNull();
            assertThat(actual.getName()).isNull();
            assertThat(actual.getCreatedAt()).isNull();
            assertThat(actual.getIngredients()).isNull();
        });
    }

    @Test
    void tacoNewInstance_whenUseSetterToOverrideValueToNull_thenFieldsReturnNull() {
        assertDoesNotThrow(() -> {
            UUID id = Generator.randomUUID();
            Date createdAt = new Date();
            String name = Generator.generateAString();
            Collection<IngredientStub> ingredients = Stream
                .generate(() -> mock(IngredientStub.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            TacoStub actual = new TacoStub()
                .setId(id)
                .setCreatedAt(createdAt)
                .setName(name)
                .setIngredients(ingredients);

            actual = actual
                .setId(null)
                .setCreatedAt(null)
                .setName(null)
                .setIngredients(null);

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(TacoStub.class);

            assertThat(actual.getId()).isNull();
            assertThat(actual.getName()).isNull();
            assertThat(actual.getCreatedAt()).isNull();
            assertThat(actual.getIngredients()).isNull();
        });
    }

    @Test
    void taco_whenAddIngredient_thenShouldBePresentInIngredientsList() {
        assertDoesNotThrow(() -> {
            UUID id = Generator.randomUUID();
            Date createdAt = new Date();
            String name = Generator.generateAString();
            Collection<IngredientStub> ingredients = Stream
                .generate(() -> mock(IngredientStub.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            Taco actual = new TacoStub()
                .setId(id)
                .setCreatedAt(createdAt)
                .setName(name)
                .setIngredients(ingredients);

            IngredientStub newOne = mock(IngredientStub.class);

            actual.addIngredient(newOne);

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(TacoStub.class);

            List<IngredientStub> ingredientsExpected = new ArrayList<>(ingredients.size() + 1) {{
                addAll(ingredients);
                add(newOne);
            }};

            assertThat(actual.getId()).isEqualTo(id);
            assertThat(actual.getName()).isEqualTo(name);
            assertThat(actual.getCreatedAt()).isEqualTo(createdAt);
            assertThat(actual.getIngredients().size()).isEqualTo(ingredientsExpected.size());
            assertThat(actual.getIngredients()).containsExactlyElementsOf(ingredientsExpected);
        });
    }

    @Test
    void taco_whenAddNullIngredient_thenShouldNotBePresentInIngredientsList() {
        assertDoesNotThrow(() -> {
            UUID id = Generator.randomUUID();
            Date createdAt = new Date();
            String name = Generator.generateAString();
            Collection<IngredientStub> ingredients = Stream
                .generate(() -> mock(IngredientStub.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            Taco actual = new TacoStub()
                .setId(id)
                .setCreatedAt(createdAt)
                .setName(name)
                .setIngredients(ingredients);

            actual.addIngredient(null);

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(TacoStub.class);

            assertThat(actual.getId()).isEqualTo(id);
            assertThat(actual.getName()).isEqualTo(name);
            assertThat(actual.getCreatedAt()).isEqualTo(createdAt);
            assertThat(actual.getIngredients()).containsExactlyElementsOf(ingredients);
        });
    }
}
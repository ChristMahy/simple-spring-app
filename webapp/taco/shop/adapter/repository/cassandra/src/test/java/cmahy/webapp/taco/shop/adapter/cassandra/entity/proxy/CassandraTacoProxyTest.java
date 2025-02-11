package cmahy.webapp.taco.shop.adapter.cassandra.entity.proxy;

import cmahy.common.helper.Generator;
import cmahy.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraTaco;
import cmahy.webapp.taco.shop.adapter.cassandra.entity.loader.TacoLoader;
import cmahy.webapp.taco.shop.kernel.domain.id.IngredientId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CassandraTacoProxyTest {

    @Mock
    private CassandraTaco taco;

    @Mock
    private TacoLoader tacoLoader;

    @Test
    void unwrap() {
        assertDoesNotThrow(() -> {
            assertThat(new CassandraTacoProxy(taco, tacoLoader).unwrap()).isSameAs(taco);
        });
    }

    @Test
    void getters() {
        assertDoesNotThrow(() -> {

            CassandraTacoProxy actual = new CassandraTacoProxy(taco, tacoLoader);

            actual.getId();
            actual.getCreatedAt();
            actual.getName();

            verify(taco).getId();
            verify(taco).getCreatedAt();
            verify(taco).getName();

            verifyNoMoreInteractions(taco);
            verifyNoInteractions(tacoLoader);
        });
    }

    @Test
    void setters() {
        assertDoesNotThrow(() -> {

            CassandraTacoProxy actual = new CassandraTacoProxy(taco, tacoLoader);

            Date createdAt = new Date();
            String name = Generator.generateAString();

            actual.setCreatedAt(createdAt);
            actual.setName(name);

            verify(taco).setCreatedAt(createdAt);
            verify(taco).setName(name);

            verify(taco, never()).setId(any(UUID.class));

            verifyNoMoreInteractions(taco);
            verifyNoInteractions(tacoLoader);
        });
    }

    @Test
    void getIngredients_whenFirstAccess_thenFetchIngredientsWithLoaderAndReturnIngredients() {
        assertDoesNotThrow(() -> {

            List<CassandraIngredientProxy> ingredients = mock(List.class);
            Set<IngredientId> ingredientIds = mock(Set.class);

            when(taco.getIngredientIds()).thenReturn(ingredientIds);
            when(tacoLoader.loadIngredients(ingredientIds)).thenReturn(ingredients);

            CassandraTacoProxy actual = new CassandraTacoProxy(taco, tacoLoader);

            assertThat(actual.getIngredients()).isSameAs(ingredients);

            verify(taco).getIngredientIds();
            verify(tacoLoader).loadIngredients(ingredientIds);

            verifyNoMoreInteractions(tacoLoader);
        });
    }

    @Test
    void getIngredients_whenAlreadyLoaded_thenReturnIngredientsWithoutLoader() {
        assertDoesNotThrow(() -> {

            List<CassandraIngredientProxy> ingredients = mock(List.class);

            CassandraTacoProxy actual = new CassandraTacoProxy(taco, tacoLoader)
                .setIngredients(ingredients);

            assertThat(actual.getIngredients()).isSameAs(ingredients);

            verify(taco, never()).getIngredientIds();

            verifyNoInteractions(tacoLoader);
        });
    }

    @Test
    void setIngredients() {
        assertDoesNotThrow(() -> {

            List<CassandraIngredientProxy> ingredients = mock(List.class);
            Set<IngredientId> ingredientIds = mock(Set.class);
            Stream<CassandraIngredientProxy> stream = mock(Stream.class, RETURNS_SELF);

            when(ingredients.stream()).thenReturn(stream);
            when(stream.collect(any(Collector.class))).thenReturn(ingredientIds);

            CassandraTacoProxy actual = new CassandraTacoProxy(taco, tacoLoader);

            actual.setIngredients(ingredients);

            assertThat(actual.getIngredients()).isSameAs(ingredients);

            verify(taco).setIngredientIds(ingredientIds);
            verifyNoInteractions(tacoLoader);
        });
    }

    @Test
    void setIngredients_whenGivenListIsNull_thenReplaceWithNewEmptyCollection() {
        assertDoesNotThrow(() -> {

            CassandraTacoProxy actual = new CassandraTacoProxy(taco, tacoLoader);

            actual.setIngredients(null);

            assertThat(actual.getIngredients()).isEmpty();

            verify(taco).setIngredientIds(any(Set.class));
            verifyNoInteractions(tacoLoader);
        });
    }

    @Test
    void addIngredient() {
        assertDoesNotThrow(() -> {

            CassandraIngredientProxy ingredient = mock(CassandraIngredientProxy.class);
            UUID ingredientId = mock(UUID.class);
            when(ingredient.getId()).thenReturn(ingredientId);

            List<CassandraIngredientProxy> ingredients = mock(List.class);
            Set<IngredientId> ingredientIds = mock(Set.class);
            Stream<CassandraIngredientProxy> stream = mock(Stream.class, RETURNS_SELF);
            when(ingredients.stream()).thenReturn(stream);
            when(stream.collect(any(Collector.class))).thenReturn(ingredientIds);

            CassandraTacoProxy actual = new CassandraTacoProxy(taco, tacoLoader)
                .setIngredients(ingredients);

            actual.addIngredient(ingredient);

            verify(ingredients).add(ingredient);
            verify(taco).addIngredientId(any(IngredientId.class));
        });
    }

    @Test
    void addIngredient_givenIngredientIsNull_thenDoNothing() {
        assertDoesNotThrow(() -> {

            List<CassandraIngredientProxy> ingredients = mock(List.class);
            Set<IngredientId> ingredientIds = mock(Set.class);
            Stream<CassandraIngredientProxy> stream = mock(Stream.class, RETURNS_SELF);
            when(ingredients.stream()).thenReturn(stream);
            when(stream.collect(any(Collector.class))).thenReturn(ingredientIds);

            CassandraTacoProxy actual = new CassandraTacoProxy(taco, tacoLoader)
                .setIngredients(ingredients);

            actual.addIngredient(null);

            verify(ingredients, never()).add(any(CassandraIngredientProxy.class));
            verify(taco, never()).addIngredientId(any(IngredientId.class));
        });
    }

    @Test
    void addIngredient_whenIngredientsIsNull_thenGenerateNewOneAndAddNewItem() {
        assertDoesNotThrow(() -> {

            CassandraIngredientProxy ingredient = mock(CassandraIngredientProxy.class);
            UUID ingredientId = mock(UUID.class);
            when(ingredient.getId()).thenReturn(ingredientId);

            CassandraTacoProxy actual = new CassandraTacoProxy(taco, tacoLoader);

            actual.addIngredient(ingredient);

            assertThat(actual.getIngredients()).containsExactly(ingredient);

            verify(taco).addIngredientId(any(IngredientId.class));
        });
    }
}
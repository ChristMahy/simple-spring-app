package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraIngredient;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraTaco;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.loader.TacoLoader;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.loader.provider.TacoLoaderProvider;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.CassandraIngredientProxyFactory;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.provider.CassandraTacoProxyFactoryProvider;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.id.IngredientId;
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
    private TacoLoaderProvider tacoLoaderProvider;

    @Mock
    private CassandraTacoProxyFactoryProvider factoryProvider;

    @Mock
    private TacoLoader tacoLoader;

    @Mock
    private CassandraIngredientProxyFactory ingredientProxyFactory;

    @Test
    void unwrap() {
        assertDoesNotThrow(() -> {
            assertThat(new CassandraTacoProxy(taco, tacoLoaderProvider, factoryProvider).unwrap()).isSameAs(taco);
        });
    }

    @Test
    void getters() {
        assertDoesNotThrow(() -> {

            CassandraTacoProxy actual = new CassandraTacoProxy(taco, tacoLoaderProvider, factoryProvider);

            actual.getId();
            actual.getCreatedAt();
            actual.getName();

            verify(taco).getId();
            verify(taco).getCreatedAt();
            verify(taco).getName();

            verifyNoMoreInteractions(taco);
            verifyNoInteractions(tacoLoaderProvider, tacoLoader);
        });
    }

    @Test
    void setters() {
        assertDoesNotThrow(() -> {

            CassandraTacoProxy actual = new CassandraTacoProxy(taco, tacoLoaderProvider, factoryProvider);

            Date createdAt = new Date();
            String name = Generator.generateAString();

            actual.setCreatedAt(createdAt);
            actual.setName(name);

            verify(taco).setCreatedAt(createdAt);
            verify(taco).setName(name);

            verify(taco, never()).setId(any(UUID.class));

            verifyNoMoreInteractions(taco);
            verifyNoInteractions(tacoLoaderProvider, tacoLoader);
        });
    }

    @Test
    void getIngredients_whenFirstAccess_thenFetchIngredientsWithLoaderAndReturnIngredients() {
        assertDoesNotThrow(() -> {

            when(factoryProvider.resolve(CassandraIngredient.class)).thenReturn(ingredientProxyFactory);
            when(tacoLoaderProvider.resolve(CassandraTaco.class)).thenReturn(tacoLoader);

            List<CassandraIngredientProxy> ingredientsProxies = new ArrayList<>();
            List<CassandraIngredient> ingredients = Stream
                .generate(() -> {
                    CassandraIngredient ingredient = mock(CassandraIngredient.class);
                    CassandraIngredientProxy ingredientProxy = mock(CassandraIngredientProxy.class);

                    ingredientsProxies.add(ingredientProxy);

                    when(ingredientProxyFactory.create(ingredient)).thenReturn(ingredientProxy);

                    return ingredient;
                })
                .limit(Generator.randomInt(50, 500))
                .toList();

            Set<IngredientId> ingredientIds = mock(Set.class);

            when(taco.getIngredientIds()).thenReturn(ingredientIds);
            when(tacoLoader.loadIngredients(ingredientIds)).thenReturn(ingredients);


            CassandraTacoProxy actual = new CassandraTacoProxy(taco, tacoLoaderProvider, factoryProvider);


            assertThat(actual.getIngredients()).containsExactlyInAnyOrderElementsOf(ingredientsProxies);

            verify(taco).getIngredientIds();
            verify(tacoLoader).loadIngredients(ingredientIds);

            verifyNoMoreInteractions(tacoLoader);
        });
    }

    @Test
    void getIngredients_whenAlreadyLoaded_thenReturnIngredientsWithoutLoader() {
        assertDoesNotThrow(() -> {

            List<CassandraIngredientProxy> ingredients = mock(List.class);

            CassandraTacoProxy actual = new CassandraTacoProxy(taco, tacoLoaderProvider, factoryProvider)
                .setIngredients(ingredients);

            assertThat(actual.getIngredients()).isSameAs(ingredients);

            verify(taco, never()).getIngredientIds();

            verifyNoInteractions(tacoLoaderProvider, tacoLoader);
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

            CassandraTacoProxy actual = new CassandraTacoProxy(taco, tacoLoaderProvider, factoryProvider);

            actual.setIngredients(ingredients);

            assertThat(actual.getIngredients()).isSameAs(ingredients);

            verify(taco).setIngredientIds(ingredientIds);
            verifyNoInteractions(tacoLoaderProvider, tacoLoader);
        });
    }

    @Test
    void setIngredients_whenGivenListIsNull_thenReplaceWithNewEmptyCollection() {
        assertDoesNotThrow(() -> {

            CassandraTacoProxy actual = new CassandraTacoProxy(taco, tacoLoaderProvider, factoryProvider);

            actual.setIngredients(null);

            assertThat(actual.getIngredients()).isEmpty();

            verify(taco).setIngredientIds(any(Set.class));
            verifyNoInteractions(tacoLoaderProvider, tacoLoader);
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

            CassandraTacoProxy actual = new CassandraTacoProxy(taco, tacoLoaderProvider, factoryProvider)
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

            CassandraTacoProxy actual = new CassandraTacoProxy(taco, tacoLoaderProvider, factoryProvider)
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

            CassandraTacoProxy actual = new CassandraTacoProxy(taco, tacoLoaderProvider, factoryProvider);

            actual.addIngredient(ingredient);

            assertThat(actual.getIngredients()).containsExactly(ingredient);

            verify(taco).addIngredientId(any(IngredientId.class));
        });
    }
}
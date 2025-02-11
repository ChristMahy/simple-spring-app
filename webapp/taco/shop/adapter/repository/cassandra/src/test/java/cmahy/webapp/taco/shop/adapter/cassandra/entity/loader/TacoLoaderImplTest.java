package cmahy.webapp.taco.shop.adapter.cassandra.entity.loader;

import cmahy.common.helper.Generator;
import cmahy.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraIngredient;
import cmahy.webapp.taco.shop.adapter.cassandra.entity.proxy.CassandraIngredientProxy;
import cmahy.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.CassandraIngredientProxyFactory;
import cmahy.webapp.taco.shop.adapter.cassandra.repository.cassandra.CassandraIngredientRepository;
import cmahy.webapp.taco.shop.kernel.domain.id.IngredientId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TacoLoaderImplTest {

    @Mock
    private CassandraIngredientRepository ingredientRepository;

    @Mock
    private CassandraIngredientProxyFactory ingredientProxyFactory;

    @InjectMocks
    private TacoLoaderImpl tacoLoaderImpl;

    @Test
    void loadIngredients() {
        assertDoesNotThrow(() -> {
            Set<IngredientId> ingredientIds = mock(Set.class);
            List<CassandraIngredientProxy> ingredientProxies = new ArrayList<>();

            List<CassandraIngredient> ingredients = Stream
                .generate(() -> {
                    CassandraIngredient ingredient = mock(CassandraIngredient.class);
                    CassandraIngredientProxy ingredientProxy = mock(CassandraIngredientProxy.class);

                    when(ingredientProxyFactory.create(ingredient)).thenReturn(ingredientProxy);

                    ingredientProxies.add(ingredientProxy);

                    return ingredient;
                })
                .limit(Generator.randomInt(100, 1000))
                .toList();

            when(ingredientRepository.findAllById(ingredientIds)).thenReturn(ingredients);

            List<CassandraIngredientProxy> actual = tacoLoaderImpl.loadIngredients(ingredientIds);

            assertThat(actual)
                .isNotNull()
                .containsExactlyInAnyOrderElementsOf(ingredientProxies);
        });
    }
}
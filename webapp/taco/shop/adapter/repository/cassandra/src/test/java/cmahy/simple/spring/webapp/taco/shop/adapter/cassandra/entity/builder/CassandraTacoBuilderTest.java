package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.builder;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraTaco;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.loader.TacoLoader;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.CassandraIngredientProxy;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.CassandraTacoProxy;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.CassandraTacoProxyFactory;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Taco;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CassandraTacoBuilderTest {

    @Mock
    private CassandraTacoProxyFactory proxyFactory;

    @Mock
    private TacoLoader tacoLoader;

    @Test
    void build() {
        assertDoesNotThrow(() -> {
            Date createdAt = new Date();
            String name = Generator.generateAString();
            Collection<CassandraIngredientProxy> ingredients = Stream
                .generate(() -> mock(CassandraIngredientProxy.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            when(proxyFactory.create(any(CassandraTaco.class)))
                .thenAnswer(invocationOnMock -> {
                    Object methodArgument = invocationOnMock.getArgument(0);

                    assertThat(methodArgument).isInstanceOf(CassandraTaco.class);

                    return new CassandraTacoProxy((CassandraTaco) methodArgument, tacoLoader);
                });

            Taco actual = new CassandraTacoBuilder(proxyFactory)
                .createdAt(createdAt)
                .name(name)
                .ingredients(ingredients)
                .build();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(CassandraTacoProxy.class);

            assertThat(actual.getId()).isNull();
            assertThat(actual.getName()).isEqualTo(name);
            assertThat(actual.getCreatedAt()).isAfterOrEqualTo(createdAt);
            assertThat(actual.getIngredients()).containsExactlyElementsOf(ingredients);
        });
    }

    @Test
    void buildWithOriginal_thenReturnOriginalWithModifiedValuesAndKeepingSameId() {
        assertDoesNotThrow(() -> {
            Date createdAt = new Date();
            String name = Generator.generateAString();
            Collection<CassandraIngredientProxy> ingredients = Stream
                .generate(() -> mock(CassandraIngredientProxy.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            CassandraTacoProxy original = new CassandraTacoProxy(
                new CassandraTaco().setId(Generator.randomUUID()),
                tacoLoader
            )
                .setName(Generator.generateAString(30))
                .setCreatedAt(new Date())
                .setIngredients(
                    Stream.generate(() -> mock(CassandraIngredientProxy.class))
                        .limit(30)
                        .toList()
                );

            Taco actual = new CassandraTacoBuilder(proxyFactory, original)
                .createdAt(createdAt)
                .name(name)
                .ingredients(ingredients)
                .build();

            assertThat(actual)
                .isNotNull()
                .isSameAs(original);

            assertThat(actual.getId()).isEqualTo(original.getId());
            assertThat(actual.getName()).isEqualTo(name);
            assertThat(actual.getCreatedAt()).isAfterOrEqualTo(createdAt);
            assertThat(actual.getIngredients()).containsExactlyElementsOf(ingredients);
        });
    }

    @Test
    void buildWithOriginal_thenReturnOriginalWithoutModifiedValuesAndKeepingSameValues() {
        assertDoesNotThrow(() -> {

            CassandraTacoProxy original = new CassandraTacoProxy(
                new CassandraTaco().setId(Generator.randomUUID()),
                tacoLoader
            )
                .setName(Generator.generateAString(30))
                .setCreatedAt(new Date())
                .setIngredients(
                    Stream.generate(() -> mock(CassandraIngredientProxy.class))
                        .limit(30)
                        .toList()
                );

            Taco actual = new CassandraTacoBuilder(proxyFactory, original).build();

            assertThat(actual)
                .isNotNull()
                .isSameAs(original);

            assertThat(actual.getId()).isEqualTo(original.getId());
            assertThat(actual.getName()).isEqualTo(original.getName());
            assertThat(actual.getCreatedAt()).isAfterOrEqualTo(original.getCreatedAt());
            assertThat(actual.getIngredients()).containsExactlyElementsOf(original.getIngredients());
        });
    }

    @Test
    void buildWithNullAsOriginal_thenBuildNewOne() {
        assertDoesNotThrow(() -> {
            Date createdAt = new Date();
            String name = Generator.generateAString();
            Collection<CassandraIngredientProxy> ingredients = Stream
                .generate(() -> mock(CassandraIngredientProxy.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            when(proxyFactory.create(any(CassandraTaco.class)))
                .thenAnswer(invocationOnMock -> {
                    Object methodArgument = invocationOnMock.getArgument(0);

                    assertThat(methodArgument).isInstanceOf(CassandraTaco.class);

                    return new CassandraTacoProxy((CassandraTaco) methodArgument, tacoLoader);
                });

            Taco actual = new CassandraTacoBuilder(proxyFactory, null)
                .createdAt(createdAt)
                .name(name)
                .ingredients(ingredients)
                .build();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(CassandraTacoProxy.class);

            assertThat(actual.getId()).isNull();
            assertThat(actual.getName()).isEqualTo(name);
            assertThat(actual.getCreatedAt()).isAfterOrEqualTo(createdAt);
            assertThat(actual.getIngredients()).containsExactlyElementsOf(ingredients);
        });
    }

}
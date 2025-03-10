package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.builder;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraIngredient;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.CassandraIngredientProxy;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.CassandraIngredientProxyFactory;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.IngredientType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CassandraIngredientBuilderTest {

    @Mock
    private CassandraIngredientProxyFactory ingredientProxyFactory;

    @Test
    void build() {
        assertDoesNotThrow(() -> {
            String name = Generator.generateAString();
            IngredientType type = Generator.randomEnum(IngredientType.class);

            when(ingredientProxyFactory.create(any(CassandraIngredient.class)))
                .thenAnswer(invocationOnMock -> {
                    Object methodArgument = invocationOnMock.getArgument(0);

                    assertThat(methodArgument).isInstanceOf(CassandraIngredient.class);

                    return new CassandraIngredientProxy((CassandraIngredient) methodArgument);
                });

            Ingredient actual = new CassandraIngredientBuilder(ingredientProxyFactory)
                .name(name)
                .type(type)
                .build();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(CassandraIngredientProxy.class);

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

            CassandraIngredientProxy original = new CassandraIngredientProxy(
                new CassandraIngredient().setId(Generator.randomUUID())
            )
                .setId(Generator.randomUUID())
                .setName(Generator.generateAString(30))
                .setType(Generator.randomEnum(IngredientType.class));

            Ingredient actual = new CassandraIngredientBuilder(ingredientProxyFactory, original)
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
    void buildWithOriginal_thenReturnOriginalWithoutModifiedValuesAndKeepingSameValues() {
        assertDoesNotThrow(() -> {

            CassandraIngredientProxy original = new CassandraIngredientProxy(
                new CassandraIngredient().setId(Generator.randomUUID())
            )
                .setId(Generator.randomUUID())
                .setName(Generator.generateAString(30))
                .setType(Generator.randomEnum(IngredientType.class));

            Ingredient actual = new CassandraIngredientBuilder(ingredientProxyFactory, original).build();

            assertThat(actual)
                .isNotNull()
                .isSameAs(original);

            assertThat(actual.getId()).isEqualTo(original.getId());
            assertThat(actual.getName()).isEqualTo(original.getName());
            assertThat(actual.getType()).isEqualTo(original.getType());
        });
    }

    @Test
    void buildWithNullAsOriginal_thenBuildNewOne() {
        assertDoesNotThrow(() -> {
            String name = Generator.generateAString();
            IngredientType type = Generator.randomEnum(IngredientType.class);

            when(ingredientProxyFactory.create(any(CassandraIngredient.class)))
                .thenAnswer(invocationOnMock -> {
                    Object methodArgument = invocationOnMock.getArgument(0);

                    assertThat(methodArgument).isInstanceOf(CassandraIngredient.class);

                    return new CassandraIngredientProxy((CassandraIngredient) methodArgument);
                });

            Ingredient actual = new CassandraIngredientBuilder(ingredientProxyFactory, null)
                .name(name)
                .type(type)
                .build();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(CassandraIngredientProxy.class);

            assertThat(actual.getId()).isNull();
            assertThat(actual.getName()).isEqualTo(name);
            assertThat(actual.getType()).isEqualTo(type);
        });
    }
}
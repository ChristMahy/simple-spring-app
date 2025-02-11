package cmahy.webapp.taco.shop.adapter.cassandra.entity.builder.factory;

import cmahy.webapp.taco.shop.adapter.cassandra.entity.builder.CassandraIngredientBuilder;
import cmahy.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraIngredient;
import cmahy.webapp.taco.shop.adapter.cassandra.entity.proxy.CassandraIngredientProxy;
import cmahy.webapp.taco.shop.kernel.domain.Ingredient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Answers.RETURNS_SELF;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class CassandraIngredientBuilderFactoryTest {

    @InjectMocks
    private CassandraIngredientBuilderFactory cassandraIngredientBuilderFactory;

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            CassandraIngredientBuilder actual = cassandraIngredientBuilderFactory.create();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(CassandraIngredientBuilder.class);
        });
    }

    @Test
    void createWithOriginal() {
        assertDoesNotThrow(() -> {
            CassandraIngredientProxy original = mock(CassandraIngredientProxy.class, RETURNS_SELF);

            CassandraIngredientBuilder actual = cassandraIngredientBuilderFactory.create(original);

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(CassandraIngredientBuilder.class);

            Ingredient actualBuilderResult = actual.build();

            assertThat(actualBuilderResult).isSameAs(original);
        });
    }
}
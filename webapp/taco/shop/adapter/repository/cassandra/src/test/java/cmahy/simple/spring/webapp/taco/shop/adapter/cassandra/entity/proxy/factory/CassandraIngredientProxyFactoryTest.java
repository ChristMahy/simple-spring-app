package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.factory;

import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraIngredient;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.CassandraIngredientProxy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class CassandraIngredientProxyFactoryTest {

    @InjectMocks
    private CassandraIngredientProxyFactory cassandraIngredientProxyFactory;

    @Test
    void create() {
        assertDoesNotThrow(() -> {

            CassandraIngredient ingredient = mock(CassandraIngredient.class);

            CassandraIngredientProxy actual = cassandraIngredientProxyFactory.create(ingredient);

            assertThat(actual).isNotNull();

            assertThat(actual.unwrap()).isSameAs(ingredient);
        });
    }
}
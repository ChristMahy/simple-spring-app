package cmahy.webapp.taco.shop.adapter.cassandra.entity.proxy.factory;

import cmahy.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraTaco;
import cmahy.webapp.taco.shop.adapter.cassandra.entity.loader.TacoLoader;
import cmahy.webapp.taco.shop.adapter.cassandra.entity.proxy.CassandraTacoProxy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class CassandraTacoProxyFactoryTest {

    @Mock
    private TacoLoader tacoLoader;

    @InjectMocks
    private CassandraTacoProxyFactory cassandraTacoProxyFactory;

    @Test
    void create() {
        assertDoesNotThrow(() -> {

            CassandraTaco taco = mock(CassandraTaco.class);

            CassandraTacoProxy actual = cassandraTacoProxyFactory.create(taco);

            assertThat(actual).isNotNull();

            assertThat(actual.unwrap()).isSameAs(taco);
        });
    }
}
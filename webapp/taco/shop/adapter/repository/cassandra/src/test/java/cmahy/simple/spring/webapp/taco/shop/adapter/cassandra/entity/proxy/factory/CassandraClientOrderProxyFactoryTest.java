package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.factory;

import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraClientOrder;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.loader.ClientOrderLoader;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.CassandraClientOrderProxy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class CassandraClientOrderProxyFactoryTest {

    @Mock
    private ClientOrderLoader clientOrderLoader;

    @InjectMocks
    private CassandraClientOrderProxyFactory cassandraClientOrderProxyFactory;

    @Test
    void create() {
        assertDoesNotThrow(() -> {

            CassandraClientOrder clientOrder = mock(CassandraClientOrder.class);

            CassandraClientOrderProxy actual = cassandraClientOrderProxyFactory.create(clientOrder);

            assertThat(actual).isNotNull();

            assertThat(actual.unwrap()).isSameAs(clientOrder);
        });
    }
}
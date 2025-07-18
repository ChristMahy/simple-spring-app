package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.builder.factory;

import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.builder.CassandraClientOrderBuilder;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraClientOrder;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.CassandraClientOrderProxy;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.CassandraClientOrderProxyFactory;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.provider.CassandraTacoProxyFactoryProvider;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.ClientOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Answers.RETURNS_SELF;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CassandraClientOrderBuilderFactoryTest {

    @Mock
    private CassandraTacoProxyFactoryProvider factoryProvider;

    private CassandraClientOrderBuilderFactory cassandraClientOrderBuilderFactory;

    @Mock
    private CassandraClientOrderProxyFactory clientOrderProxyFactory;

    @BeforeEach
    void setUp() {
        when(factoryProvider.resolve(CassandraClientOrder.class)).thenReturn(clientOrderProxyFactory);

        cassandraClientOrderBuilderFactory = new CassandraClientOrderBuilderFactory(factoryProvider);
    }

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            CassandraClientOrderBuilder actual = cassandraClientOrderBuilderFactory.create();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(CassandraClientOrderBuilder.class);
        });
    }

    @Test
    void createWithOriginal() {
        assertDoesNotThrow(() -> {
            CassandraClientOrderProxy clientOrder = mock(CassandraClientOrderProxy.class, RETURNS_SELF);

            CassandraClientOrderBuilder actual = cassandraClientOrderBuilderFactory.create(clientOrder);

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(CassandraClientOrderBuilder.class);

            ClientOrder actualBuilderResult = actual.build();

            assertThat(actualBuilderResult).isSameAs(clientOrder);
        });
    }
}
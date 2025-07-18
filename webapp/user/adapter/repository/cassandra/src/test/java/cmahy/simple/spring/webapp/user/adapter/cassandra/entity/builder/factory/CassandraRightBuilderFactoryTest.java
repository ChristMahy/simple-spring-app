package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.builder.factory;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.builder.CassandraRightBuilder;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraRight;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraRightProxy;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraRightProxyFactory;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.provider.CassandraUserProxyFactoryProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CassandraRightBuilderFactoryTest {

    @Mock
    private CassandraUserProxyFactoryProvider factoryProvider;

    private CassandraRightBuilderFactory cassandraRightBuilderFactory;

    @Mock
    private CassandraRightProxyFactory cassandraRightProxyFactory;

    @BeforeEach
    void setUp() {
        when(factoryProvider.resolve(CassandraRight.class)).thenReturn(cassandraRightProxyFactory);

        cassandraRightBuilderFactory = new CassandraRightBuilderFactory(factoryProvider);
    }

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            CassandraRightProxy rightProxy = mock(CassandraRightProxy.class, RETURNS_SELF);

            when(cassandraRightProxyFactory.create(any(CassandraRight.class))).thenReturn(rightProxy);


            CassandraRightBuilder actual = cassandraRightBuilderFactory.create();


            assertThat(actual)
                .isNotNull()
                .isInstanceOf(CassandraRightBuilder.class);

            CassandraRightProxy actualBuilt = actual.build();

            assertThat(actualBuilt).isNotNull().isSameAs(rightProxy);
        });
    }

    @Test
    void testCreate() {
        assertDoesNotThrow(() -> {
            CassandraRightProxy rightProxy = mock(CassandraRightProxy.class, RETURNS_SELF);
            when(rightProxy.getRoles()).thenReturn(mock(Collection.class));


            CassandraRightBuilder actual = cassandraRightBuilderFactory.create(rightProxy);


            assertThat(actual)
                .isNotNull()
                .isInstanceOf(CassandraRightBuilder.class);

            CassandraRightProxy actualBuilt = actual.build();

            assertThat(actualBuilt).isNotNull().isSameAs(rightProxy);
        });
    }

}
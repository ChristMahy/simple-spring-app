package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.builder.factory;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.builder.CassandraRoleBuilder;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraRole;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraRoleProxy;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraRoleProxyFactory;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.provider.CassandraUserProxyFactoryProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CassandraRoleBuilderFactoryTest {

    @Mock
    private CassandraUserProxyFactoryProvider factoryProvider;

    private CassandraRoleBuilderFactory cassandraRoleBuilderFactory;

    @Mock
    private CassandraRoleProxyFactory cassandraRoleProxyFactory;

    @BeforeEach
    void setUp() {
        when(factoryProvider.resolve(CassandraRole.class)).thenReturn(cassandraRoleProxyFactory);

        cassandraRoleBuilderFactory = new CassandraRoleBuilderFactory(factoryProvider);
    }

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            CassandraRoleProxy roleProxy = mock(CassandraRoleProxy.class, RETURNS_SELF);

            when(cassandraRoleProxyFactory.create(any(CassandraRole.class))).thenReturn(roleProxy);

            CassandraRoleBuilder actual = cassandraRoleBuilderFactory.create();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(CassandraRoleBuilder.class);

            CassandraRoleProxy actualBuilt = actual.build();

            assertThat(actualBuilt).isNotNull();
        });
    }

    @Test
    void createWithOriginalRole() {
        assertDoesNotThrow(() -> {
            CassandraRoleProxy role = mock(CassandraRoleProxy.class, RETURNS_SELF);

            CassandraRoleBuilder actual = cassandraRoleBuilderFactory.create(role);

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(CassandraRoleBuilder.class);

            CassandraRoleProxy actualBuilderResult = actual.build();

            assertThat(actualBuilderResult).isSameAs(role);
        });
    }
}
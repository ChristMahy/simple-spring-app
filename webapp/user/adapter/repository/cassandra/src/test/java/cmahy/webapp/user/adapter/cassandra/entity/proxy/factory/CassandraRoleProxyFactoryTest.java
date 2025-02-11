package cmahy.webapp.user.adapter.cassandra.entity.proxy.factory;

import cmahy.webapp.user.adapter.cassandra.entity.domain.CassandraRole;
import cmahy.webapp.user.adapter.cassandra.entity.loader.RoleLoader;
import cmahy.webapp.user.adapter.cassandra.entity.proxy.CassandraRoleProxy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class CassandraRoleProxyFactoryTest {

    @Mock
    private RoleLoader roleLoader;

    @InjectMocks
    private CassandraRoleProxyFactory cassandraRoleProxyFactory;

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            CassandraRole cassandraRole = mock(CassandraRole.class);

            CassandraRoleProxy actual = cassandraRoleProxyFactory.create(cassandraRole);

            assertThat(actual).isNotNull();

            assertThat(actual.unwrap()).isSameAs(cassandraRole);
        });
    }
}
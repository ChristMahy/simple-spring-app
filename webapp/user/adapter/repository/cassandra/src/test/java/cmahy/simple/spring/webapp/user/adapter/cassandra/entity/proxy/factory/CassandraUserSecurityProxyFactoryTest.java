package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraUserSecurityImpl;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader.UserSecurityLoader;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraUserSecurityProxy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class CassandraUserSecurityProxyFactoryTest {

    @Mock
    private UserSecurityLoader userSecurityLoader;

    @InjectMocks
    private CassandraUserSecurityProxyFactory cassandraUserSecurityProxyFactory;

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            CassandraUserSecurityImpl userSecurity = mock(CassandraUserSecurityImpl.class);

            CassandraUserSecurityProxy actual = cassandraUserSecurityProxyFactory.create(userSecurity);

            assertThat(actual).isNotNull();

            assertThat(actual.unwrap()).isSameAs(userSecurity);
        });
    }
}
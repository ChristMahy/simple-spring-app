package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraUserImpl;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraUserProxy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class CassandraUserProxyFactoryTest {

    @InjectMocks
    private CassandraUserProxyFactory cassandraUserProxyFactory;

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            CassandraUserImpl user = mock(CassandraUserImpl.class);


            CassandraUserProxy actual = cassandraUserProxyFactory.create(user);


            assertThat(actual).isNotNull();

            assertThat(actual.unwrap()).isSameAs(user);
        });
    }
}
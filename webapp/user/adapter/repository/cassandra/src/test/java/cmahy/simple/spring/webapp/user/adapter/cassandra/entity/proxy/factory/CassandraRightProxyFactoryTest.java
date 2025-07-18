package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraRight;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraRightProxy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class CassandraRightProxyFactoryTest {

    @InjectMocks
    private CassandraRightProxyFactory cassandraRightProxyFactory;

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            CassandraRight right = mock(CassandraRight.class);


            CassandraRightProxy actual = cassandraRightProxyFactory.create(right);


            assertThat(actual).isNotNull();

            assertThat(actual.unwrap()).isNotNull().isSameAs(right);
        });
    }
}
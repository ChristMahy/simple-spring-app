package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.builder.factory;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.builder.CassandraUserBuilder;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraUserImpl;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraUserProxy;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraUserProxyFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CassandraUserBuilderFactoryTest {

    @Mock
    private CassandraUserProxyFactory cassandraUserProxyFactory;

    @InjectMocks
    private CassandraUserBuilderFactory cassandraUserBuilderFactory;

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            CassandraUserProxy userProxy = mock(CassandraUserProxy.class, RETURNS_SELF);

            when(cassandraUserProxyFactory.create(any(CassandraUserImpl.class))).thenReturn(userProxy);

            CassandraUserBuilder actual = cassandraUserBuilderFactory.create();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(CassandraUserBuilder.class);

            CassandraUserProxy actualBuilt = actual.build();

            assertThat(actualBuilt).isNotNull();
        });
    }

    @Test
    void createWithOriginalUser() {
        assertDoesNotThrow(() -> {
            CassandraUserProxy user = mock(CassandraUserProxy.class, RETURNS_SELF);
            when(user.getRoles()).thenReturn(mock(HashSet.class));

            CassandraUserBuilder actual = cassandraUserBuilderFactory.create(user);

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(CassandraUserBuilder.class);

            CassandraUserProxy actualBuilderResult = actual.build();

            assertThat(actualBuilderResult).isSameAs(user);
        });
    }

}
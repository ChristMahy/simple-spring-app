package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.builder.factory;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.builder.CassandraUserSecurityBuilder;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraUserSecurityImpl;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraUserSecurityProxy;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraUserSecurityProxyFactory;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.provider.CassandraUserProxyFactoryProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Answers.RETURNS_SELF;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CassandraUserSecurityBuilderFactoryTest {

    @Mock
    private CassandraUserProxyFactoryProvider factoryProvider;

    private CassandraUserSecurityBuilderFactory cassandraUserSecurityBuilderFactory;

    @Mock
    private CassandraUserSecurityProxyFactory userSecurityProxyFactory;

    @BeforeEach
    void setUp() {
        when(factoryProvider.resolve(CassandraUserSecurityImpl.class)).thenReturn(userSecurityProxyFactory);

        cassandraUserSecurityBuilderFactory = new CassandraUserSecurityBuilderFactory(factoryProvider);
    }

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            CassandraUserSecurityProxy userProxy = mock(CassandraUserSecurityProxy.class, RETURNS_SELF);

            when(userSecurityProxyFactory.create(any(CassandraUserSecurityImpl.class))).thenReturn(userProxy);

            CassandraUserSecurityBuilder actual = cassandraUserSecurityBuilderFactory.create();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(CassandraUserSecurityBuilder.class);

            CassandraUserSecurityProxy actualBuilt = actual.build();

            assertThat(actualBuilt).isNotNull();
        });
    }

    @Test
    void createWithOriginalUser() {
        assertDoesNotThrow(() -> {
            CassandraUserSecurityProxy user = mock(CassandraUserSecurityProxy.class, RETURNS_SELF);
            when(user.getRoles()).thenReturn(mock(HashSet.class));

            CassandraUserSecurityBuilder actual = cassandraUserSecurityBuilderFactory.create(user);

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(CassandraUserSecurityBuilder.class);

            CassandraUserSecurityProxy actualBuilderResult = actual.build();

            assertThat(actualBuilderResult).isSameAs(user);
        });
    }
}
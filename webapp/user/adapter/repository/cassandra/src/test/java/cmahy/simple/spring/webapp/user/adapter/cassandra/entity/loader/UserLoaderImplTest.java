package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraRole;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraRoleProxy;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraRoleProxyFactory;
import cmahy.simple.spring.webapp.user.adapter.cassandra.repository.cassandra.CassandraRoleRepositoryImpl;
import cmahy.simple.spring.webapp.user.kernel.domain.id.RoleId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserLoaderImplTest {

    @Mock
    private CassandraRoleRepositoryImpl cassandraRoleRepository;

    @Mock
    private CassandraRoleProxyFactory cassandraRoleProxyFactory;

    @InjectMocks
    private UserLoaderImpl userLoaderImpl;

    @Test
    void loadRoles() {
        assertDoesNotThrow(() -> {
            Set<CassandraRoleProxy> roleProxies = new HashSet<>();

            Set<RoleId> roleIds = mock(HashSet.class);
            List<CassandraRole> roles = Stream
                .generate(() -> {
                    CassandraRole role = mock(CassandraRole.class);
                    CassandraRoleProxy roleProxy = mock(CassandraRoleProxy.class);

                    when(cassandraRoleProxyFactory.create(role)).thenReturn(roleProxy);

                    roleProxies.add(roleProxy);

                    return role;
                })
                .limit(Generator.randomInt(100, 1000))
                .toList();

            when(cassandraRoleRepository.findAllById(roleIds)).thenReturn(roles);

            Set<CassandraRoleProxy> actual = userLoaderImpl.loadRoles(roleIds);

            assertThat(actual)
                .isNotNull()
                .containsExactlyInAnyOrderElementsOf(roleProxies);
        });
    }
}
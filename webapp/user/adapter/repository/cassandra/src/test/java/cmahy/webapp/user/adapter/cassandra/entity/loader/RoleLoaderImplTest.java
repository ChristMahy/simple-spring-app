package cmahy.webapp.user.adapter.cassandra.entity.loader;

import cmahy.common.helper.Generator;
import cmahy.webapp.user.adapter.cassandra.entity.domain.CassandraUser;
import cmahy.webapp.user.adapter.cassandra.entity.domain.CassandraUserImpl;
import cmahy.webapp.user.adapter.cassandra.entity.proxy.CassandraUserProxy;
import cmahy.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraUserProxyFactory;
import cmahy.webapp.user.adapter.cassandra.repository.cassandra.CassandraUserRepositoryImpl;
import cmahy.webapp.user.kernel.domain.id.RoleId;
import cmahy.webapp.user.kernel.domain.id.UserId;
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
class RoleLoaderImplTest {

    @Mock
    private CassandraUserRepositoryImpl cassandraUserRepository;

    @Mock
    private CassandraUserProxyFactory cassandraUserProxyFactory;

    @InjectMocks
    private RoleLoaderImpl roleLoaderImpl;

    @Test
    void loadUsers() {
        assertDoesNotThrow(() -> {
            RoleId roleId = mock(RoleId.class);

            Set<CassandraUserProxy> userProxies = new HashSet<>();

            List<CassandraUserImpl> users = Stream
                .generate(() -> {
                    CassandraUserImpl user = mock(CassandraUserImpl.class);
                    CassandraUserProxy userProxy = mock(CassandraUserProxy.class);

                    when(cassandraUserProxyFactory.create(user)).thenReturn(userProxy);

                    userProxies.add(userProxy);

                    return user;
                })
                .limit(Generator.randomInt(100, 1000))
                .toList();

            when(cassandraUserRepository.findByCassandraRoleId(roleId)).thenReturn(users);

            List<CassandraUserProxy> actual = roleLoaderImpl.loadUsers(roleId);

            assertThat(actual)
                .isNotNull()
                .containsExactlyInAnyOrderElementsOf(userProxies);
        });
    }
}
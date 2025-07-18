package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraRight;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraRole;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader.RightLoader;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader.provider.UserLoaderProvider;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraRoleProxyFactory;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.provider.CassandraUserProxyFactoryProvider;
import cmahy.simple.spring.webapp.user.kernel.domain.id.RightId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CassandraRightProxyTest {

    @Mock
    private CassandraRight right;

    @Mock
    private UserLoaderProvider userLoaderProvider;

    @Mock
    private CassandraUserProxyFactoryProvider factoryProvider;

    @Mock
    private RightLoader rightLoader;

    @Mock
    private CassandraRoleProxyFactory roleProxyFactory;

    @Test
    void unwrap() {
        assertDoesNotThrow(() -> {
            assertThat(new CassandraRightProxy(right, userLoaderProvider, factoryProvider).unwrap()).isSameAs(right);
        });
    }

    @Test
    void proxySetters() {
        assertDoesNotThrow(() -> {

            CassandraRightProxy actual = new CassandraRightProxy(right, userLoaderProvider, factoryProvider);

            String newName = Generator.generateAString();

            actual.setName(newName);


            verify(right).setName(newName);

            verifyNoMoreInteractions(right);
            verifyNoInteractions(rightLoader, userLoaderProvider, factoryProvider);

        });
    }

    @Test
    void proxyGetters() {
        assertDoesNotThrow(() -> {

            CassandraRightProxy actual = new CassandraRightProxy(right, userLoaderProvider, factoryProvider);

            actual.getId();
            actual.getName();


            verify(right).getId();
            verify(right).getName();

            verifyNoMoreInteractions(right);
            verifyNoInteractions(rightLoader, userLoaderProvider, factoryProvider);

        });
    }

    @Test
    void rolesSetter_whenRolesSetterIsCalled_thenRightNeverCalled_RightDoesntOwnReferenceToRole() {
        assertDoesNotThrow(() -> {

            List<CassandraRoleProxy> roles = mock(List.class);


            CassandraRightProxy actual = new CassandraRightProxy(right, userLoaderProvider, factoryProvider);

            actual.setRoles(roles);


            verifyNoInteractions(rightLoader, userLoaderProvider, factoryProvider);

        });
    }

    @Test
    void roleGetters_whenRolesAreLoaded_thenReturnRolesWithoutCallLoader() {
        assertDoesNotThrow(() -> {

            List<CassandraRoleProxy> roles = mock(List.class);


            CassandraRightProxy actual = new CassandraRightProxy(right, userLoaderProvider, factoryProvider)
                .setRoles(roles);

            assertThat(actual.getRoles()).isSameAs(roles);


            verifyNoInteractions(right, rightLoader, userLoaderProvider, factoryProvider);

        });
    }

    @Test
    void roleGetters_whenRolesAreNotLoaded_thenCallLoaderAndReturnRoles() {
        assertDoesNotThrow(() -> {

            List<CassandraRoleProxy> rolesProxies = new ArrayList<>();
            List<CassandraRole> roles = Stream
                .generate(() -> {
                    CassandraRole role = mock(CassandraRole.class);
                    CassandraRoleProxy roleProxy = mock(CassandraRoleProxy.class);

                    rolesProxies.add(roleProxy);

                    when(roleProxyFactory.create(role)).thenReturn(roleProxy);

                    return role;
                })
                .limit(Generator.randomInt(50,500))
                .toList();

            when(factoryProvider.resolve(CassandraRole.class)).thenReturn(roleProxyFactory);
            when(userLoaderProvider.resolve(CassandraRight.class)).thenReturn(rightLoader);

            when(rightLoader.loadRoles(any(RightId.class))).thenReturn(roles);


            CassandraRightProxy actual = new CassandraRightProxy(right, userLoaderProvider, factoryProvider);


            assertThat(actual.getRoles()).containsExactlyInAnyOrderElementsOf(rolesProxies);


            verify(right).getId();
            verify(rightLoader).loadRoles(any(RightId.class));

            verifyNoMoreInteractions(right, rightLoader);

        });
    }
}
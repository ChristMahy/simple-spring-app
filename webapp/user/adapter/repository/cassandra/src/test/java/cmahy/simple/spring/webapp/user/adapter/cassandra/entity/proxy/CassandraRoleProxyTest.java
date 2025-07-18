package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.*;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader.RoleLoader;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader.provider.UserLoaderProvider;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraRightProxyFactory;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraUserProxyFactory;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.provider.CassandraUserProxyFactoryProvider;
import cmahy.simple.spring.webapp.user.kernel.domain.id.RightId;
import cmahy.simple.spring.webapp.user.kernel.domain.id.RoleId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CassandraRoleProxyTest {

    @Mock
    private CassandraRole role;

    @Mock
    private UserLoaderProvider userLoaderProvider;

    @Mock
    private CassandraUserProxyFactoryProvider factoryProvider;

    @Mock
    private RoleLoader roleLoader;

    @Mock
    private CassandraUserProxyFactory userProxyFactory;

    @Mock
    private CassandraRightProxyFactory rightProxyFactory;

    @Test
    void unwrap() {
        assertDoesNotThrow(() -> {
            assertThat(new CassandraRoleProxy(role, userLoaderProvider, factoryProvider).unwrap()).isSameAs(role);
        });
    }

    @Test
    void proxySetters() {
        assertDoesNotThrow(() -> {
            CassandraRoleProxy actual = new CassandraRoleProxy(role, userLoaderProvider, factoryProvider);

            String newName = Generator.generateAString();

            actual.setName(newName);

            verify(role).setName(newName);
            verifyNoMoreInteractions(role);
            verifyNoInteractions(userLoaderProvider, roleLoader);
        });
    }

    @Test
    void proxyGetters() {
        assertDoesNotThrow(() -> {
            CassandraRoleProxy actual = new CassandraRoleProxy(role, userLoaderProvider, factoryProvider);

            actual.getId();
            actual.getName();

            verify(role).getId();
            verify(role).getName();

            verifyNoMoreInteractions(role);
            verifyNoInteractions(userLoaderProvider, roleLoader);
        });
    }

    @Test
    void usersSetter_whenUsersSetterIsCalled_thenRoleNeverCalled_RoleDoesntOwnReferenceToUser() {
        assertDoesNotThrow(() -> {
            List<CassandraUserProxy> users = mock(List.class);

            CassandraRoleProxy actual = new CassandraRoleProxy(role, userLoaderProvider, factoryProvider);

            actual.setUsers(users);

            verifyNoInteractions(role, userLoaderProvider, roleLoader);
        });
    }

    @Test
    void userGetters_whenUsersAreLoaded_thenReturnUsersWithoutCallLoader() {
        assertDoesNotThrow(() -> {
            List<CassandraUserProxy> users = mock(List.class);

            CassandraRoleProxy actual = new CassandraRoleProxy(role, userLoaderProvider, factoryProvider)
                .setUsers(users);

            assertThat(actual.getUsers()).isSameAs(users);

            verifyNoInteractions(role, userLoaderProvider, roleLoader);
        });
    }

    @Test
    void userGetters_whenUsersAreNotLoaded_thenCallLoaderAndReturnUsers() {
        assertDoesNotThrow(() -> {

            List<CassandraUserProxy> usersProxies = new ArrayList<>();
            List<CassandraUserImpl> users = Stream
                .generate(() -> {
                    CassandraUserImpl user = mock(CassandraUserImpl.class);
                    CassandraUserProxy userProxy = mock(CassandraUserProxy.class);

                    usersProxies.add(userProxy);

                    when(userProxyFactory.create(user)).thenReturn(userProxy);

                    return user;
                })
                .limit(Generator.randomInt(40, 300))
                .toList();

            when(factoryProvider.resolve(CassandraUserImpl.class)).thenReturn(userProxyFactory);
            when(userLoaderProvider.resolve(CassandraRole.class)).thenReturn(roleLoader);

            when(roleLoader.loadUsers(any(RoleId.class))).thenReturn(users);


            CassandraRoleProxy actual = new CassandraRoleProxy(role, userLoaderProvider, factoryProvider);

            assertThat(actual.getUsers()).containsExactlyInAnyOrderElementsOf(usersProxies);


            verify(role).getId();
            verify(roleLoader).loadUsers(any(RoleId.class));
            verifyNoMoreInteractions(role, roleLoader);

        });
    }

    @Test
    void getRights_whenFirstAccess_thenFetchRightsWithLoaderAndReturnRights() {
        assertDoesNotThrow(() -> {

            when(factoryProvider.resolve(CassandraRight.class)).thenReturn(rightProxyFactory);
            when(userLoaderProvider.resolve(CassandraRole.class)).thenReturn(roleLoader);

            List<CassandraRightProxy> rightsProxies =  new ArrayList<>();
            List<CassandraRight> rights = Stream
                .generate(() -> {
                    CassandraRight right = mock(CassandraRight.class);

                    CassandraRightProxy rightProxy = mock(CassandraRightProxy.class);
                    rightsProxies.add(rightProxy);

                    when(rightProxyFactory.create(right)).thenReturn(rightProxy);

                    return right;
                })
                .limit(Generator.randomInt(50, 500))
                .toList();

            List<RightId> rightsIds = Stream
                .generate(() -> mock(RightId.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            when(role.getCassandraRightIds()).thenReturn(rightsIds);
            when(roleLoader.loadRights(new HashSet<>(rightsIds))).thenReturn(rights);


            CassandraRoleProxy actual = new CassandraRoleProxy(role, userLoaderProvider, factoryProvider);


            assertThat(actual.getRights()).containsExactlyInAnyOrderElementsOf(rightsProxies);

            verify(role).getCassandraRightIds();
            verify(roleLoader).loadRights(new HashSet<>(rightsIds));

            verifyNoMoreInteractions(role, roleLoader);
        });
    }

    @Test
    void getRights_whenAlreadyLoaded_thenReturnRightsWithoutLoader() {
        assertDoesNotThrow(() -> {

            List<CassandraRightProxy> rights = mock(List.class);

            CassandraRoleProxy actual = new CassandraRoleProxy(role, userLoaderProvider, factoryProvider)
                .setRights(rights);

            assertThat(actual.getRights()).isSameAs(rights);


            verifyNoInteractions(userLoaderProvider, roleLoader);
        });
    }

    @Test
    void setRights() {
        assertDoesNotThrow(() -> {

            List<CassandraRightProxy> rights = mock(List.class);
            List<RightId> rightsIds = mock(List.class);
            Stream<CassandraRightProxy> stream = mock(Stream.class, RETURNS_SELF);

            when(rights.stream()).thenReturn(stream);
            when(stream.toList()).thenAnswer(_ -> rightsIds);


            CassandraRoleProxy actual = new CassandraRoleProxy(role, userLoaderProvider, factoryProvider);

            actual.setRights(rights);


            assertThat(actual.getRights()).isSameAs(rights);

            verify(role).setCassandraRightIds(rightsIds);
            verifyNoInteractions(userLoaderProvider, roleLoader);

        });
    }

    @Test
    void setRights_whenGivenListIsNull_thenReplaceWithNewEmptyCollection() {
        assertDoesNotThrow(() -> {

            CassandraRoleProxy actual = new CassandraRoleProxy(role, userLoaderProvider, factoryProvider);

            actual.setRights(null);


            assertThat(actual.getRights()).isEmpty();

            verify(role).setCassandraRightIds(anyList());
            verifyNoInteractions(userLoaderProvider, roleLoader);

        });
    }
}
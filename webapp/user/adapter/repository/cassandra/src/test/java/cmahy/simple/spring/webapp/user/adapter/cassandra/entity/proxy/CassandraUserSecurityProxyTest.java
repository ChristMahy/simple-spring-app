package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraRole;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraUserSecurityImpl;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader.UserSecurityLoader;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader.provider.UserLoaderProvider;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraRoleProxyFactory;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.provider.CassandraUserProxyFactoryProvider;
import cmahy.simple.spring.webapp.user.kernel.domain.AuthProvider;
import cmahy.simple.spring.webapp.user.kernel.domain.id.RoleId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CassandraUserSecurityProxyTest {

    @Mock
    private CassandraUserSecurityImpl userSecurity;

    @Mock
    private UserLoaderProvider userLoaderProvider;

    @Mock
    private CassandraUserProxyFactoryProvider factoryProvider;

    @Mock
    private UserSecurityLoader userLoader;

    @Mock
    private CassandraRoleProxyFactory roleProxyFactory;

    @Test
    void unwrap() {
        assertDoesNotThrow(() -> {
            assertThat(new CassandraUserSecurityProxy(userSecurity, userLoaderProvider, factoryProvider).unwrap()).isSameAs(userSecurity);
        });
    }

    @Test
    void getters() {
        assertDoesNotThrow(() -> {

            CassandraUserSecurityProxy actual = new CassandraUserSecurityProxy(userSecurity, userLoaderProvider, factoryProvider);

            actual.getId();
            actual.getUserName();
            actual.getPassword();
            actual.getFullName();
            actual.getStreet();
            actual.getCity();
            actual.getState();
            actual.getZip();
            actual.getPhoneNumber();
            actual.getAuthProvider();
            actual.getExpired();
            actual.getLocked();
            actual.getEnabled();
            actual.getCredentialsExpired();

            verify(userSecurity).getId();
            verify(userSecurity).getUserName();
            verify(userSecurity).getPassword();
            verify(userSecurity).getFullName();
            verify(userSecurity).getStreet();
            verify(userSecurity).getCity();
            verify(userSecurity).getState();
            verify(userSecurity).getZip();
            verify(userSecurity).getPhoneNumber();
            verify(userSecurity).getAuthProvider();
            verify(userSecurity).getExpired();
            verify(userSecurity).getLocked();
            verify(userSecurity).getEnabled();
            verify(userSecurity).getCredentialsExpired();

            verifyNoMoreInteractions(userSecurity);
            verifyNoInteractions(userLoaderProvider, userLoader);
        });
    }

    @Test
    void setters() {
        assertDoesNotThrow(() -> {

            CassandraUserSecurityProxy actual = new CassandraUserSecurityProxy(userSecurity, userLoaderProvider, factoryProvider);

            String userName = Generator.generateAString();
            byte[] password = Generator.randomBytes(100);
            String fullName = Generator.generateAString();
            String street = Generator.generateAString();
            String city = Generator.generateAString();
            String state = Generator.generateAString();
            String zip = Generator.generateAString();
            String phoneNumber = Generator.generateAString();
            AuthProvider authProvider = Generator.randomEnum(AuthProvider.class);
            Boolean expired = Generator.randomBoolean();
            Boolean locked = Generator.randomBoolean();
            Boolean enabled = Generator.randomBoolean();
            Boolean credentialsExpired = Generator.randomBoolean();

            actual.setUserName(userName);
            actual.setPassword(password);
            actual.setFullName(fullName);
            actual.setStreet(street);
            actual.setCity(city);
            actual.setState(state);
            actual.setZip(zip);
            actual.setPhoneNumber(phoneNumber);
            actual.setAuthProvider(authProvider);
            actual.setExpired(expired);
            actual.setLocked(locked);
            actual.setEnabled(enabled);
            actual.setCredentialsExpired(credentialsExpired);

            verify(userSecurity).setUserName(userName);
            verify(userSecurity).setPassword(password);
            verify(userSecurity).setFullName(fullName);
            verify(userSecurity).setStreet(street);
            verify(userSecurity).setCity(city);
            verify(userSecurity).setState(state);
            verify(userSecurity).setZip(zip);
            verify(userSecurity).setPhoneNumber(phoneNumber);
            verify(userSecurity).setAuthProvider(authProvider);
            verify(userSecurity).setExpired(expired);
            verify(userSecurity).setLocked(locked);
            verify(userSecurity).setEnabled(enabled);
            verify(userSecurity).setCredentialsExpired(credentialsExpired);

            verifyNoMoreInteractions(userSecurity);
            verifyNoInteractions(userLoaderProvider, userLoader);
        });
    }

    @Test
    void getRoles_whenFirstAccess_thenFetchRolesWithLoaderAndReturnRoles() {
        assertDoesNotThrow(() -> {

            when(userLoaderProvider.resolve(CassandraUserSecurityImpl.class)).thenReturn(userLoader);
            when(factoryProvider.resolve(CassandraRole.class)).thenReturn(roleProxyFactory);

            Set<CassandraRoleProxy> rolesProxies = new HashSet<>();
            Set<CassandraRole> roles = Stream
                .generate(() -> {
                    CassandraRole role = mock(CassandraRole.class);

                    CassandraRoleProxy roleProxy = mock(CassandraRoleProxy.class);
                    rolesProxies.add(roleProxy);

                    when(roleProxyFactory.create(role)).thenReturn(roleProxy);

                    return role;
                })
                .limit(Generator.randomInt(50, 500))
                .collect(Collectors.toSet());

            Set<RoleId> roleIds = mock(Set.class);

            when(userSecurity.getCassandraRoleIds()).thenReturn(roleIds);
            when(userLoader.loadRoles(roleIds)).thenReturn(roles);


            CassandraUserSecurityProxy actual = new CassandraUserSecurityProxy(userSecurity, userLoaderProvider, factoryProvider);


            assertThat(actual.getRoles()).containsExactlyInAnyOrderElementsOf(rolesProxies);

            verify(userSecurity).getCassandraRoleIds();
            verify(userLoader).loadRoles(roleIds);

            verifyNoMoreInteractions(userSecurity, userLoader);
        });
    }

    @Test
    void getRoles_whenAlreadyLoaded_thenReturnRolesWithoutLoader() {
        assertDoesNotThrow(() -> {

            Set<CassandraRoleProxy> roles = mock(Set.class);

            CassandraUserSecurityProxy actual = new CassandraUserSecurityProxy(userSecurity, userLoaderProvider, factoryProvider)
                .setRoles(roles);

            assertThat(actual.getRoles()).isSameAs(roles);

            verify(userSecurity, never()).getCassandraRoleIds();

            verifyNoInteractions(userLoaderProvider, userLoader);
        });
    }

    @Test
    void setRoles() {
        assertDoesNotThrow(() -> {

            Set<CassandraRoleProxy> roles = mock(Set.class);
            Set<RoleId> roleIds = mock(Set.class);
            Stream<CassandraRoleProxy> stream = mock(Stream.class);

            when(roles.stream()).thenReturn(stream);
            when(stream.map(any(Function.class))).thenReturn(stream);
            when(stream.collect(any(Collector.class))).thenReturn(roleIds);

            CassandraUserSecurityProxy actual = new CassandraUserSecurityProxy(userSecurity, userLoaderProvider, factoryProvider);

            actual.setRoles(roles);

            assertThat(actual.getRoles()).isSameAs(roles);

            verify(userSecurity).setCassandraRoleIds(roleIds);
            verifyNoInteractions(userLoaderProvider, userLoader);
        });
    }

    @Test
    void setRoles_whenGivenListIsNull_thenReplaceWithNewEmptyCollection() {
        assertDoesNotThrow(() -> {

            CassandraUserSecurityProxy actual = new CassandraUserSecurityProxy(userSecurity, userLoaderProvider, factoryProvider);

            actual.setRoles(null);

            assertThat(actual.getRoles()).isEmpty();

            verify(userSecurity).setCassandraRoleIds(any(Set.class));
            verifyNoInteractions(userLoaderProvider, userLoader);
        });
    }
}
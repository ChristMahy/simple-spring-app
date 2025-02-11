package cmahy.webapp.user.adapter.cassandra.entity.proxy;

import cmahy.common.helper.Generator;
import cmahy.webapp.user.adapter.cassandra.entity.domain.CassandraUserImpl;
import cmahy.webapp.user.adapter.cassandra.entity.loader.UserLoader;
import cmahy.webapp.user.kernel.domain.id.RoleId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CassandraUserProxyTest {

    @Mock
    private CassandraUserImpl user;

    @Mock
    private UserLoader userLoader;

    @Test
    void unwrap() {
        assertDoesNotThrow(() -> {
            assertThat(new CassandraUserProxy(user, userLoader).unwrap()).isSameAs(user);
        });
    }

    @Test
    void getters() {
        assertDoesNotThrow(() -> {

            CassandraUserProxy actual = new CassandraUserProxy(user, userLoader);

            actual.getId();
            actual.getUserName();
            actual.getPassword();
            actual.getFullName();
            actual.getStreet();
            actual.getCity();
            actual.getState();
            actual.getZip();
            actual.getPhoneNumber();

            verify(user).getId();
            verify(user).getUserName();
            verify(user).getPassword();
            verify(user).getFullName();
            verify(user).getStreet();
            verify(user).getCity();
            verify(user).getState();
            verify(user).getZip();
            verify(user).getPhoneNumber();

            verifyNoMoreInteractions(user);
            verifyNoInteractions(userLoader);
        });
    }

    @Test
    void setters() {
        assertDoesNotThrow(() -> {

            CassandraUserProxy actual = new CassandraUserProxy(user, userLoader);

            String userName = Generator.generateAString();
            byte[] password = Generator.randomBytes(100);
            String fullName = Generator.generateAString();
            String street = Generator.generateAString();
            String city = Generator.generateAString();
            String state = Generator.generateAString();
            String zip = Generator.generateAString();
            String phoneNumber = Generator.generateAString();

            actual.setUserName(userName);
            actual.setPassword(password);
            actual.setFullName(fullName);
            actual.setStreet(street);
            actual.setCity(city);
            actual.setState(state);
            actual.setZip(zip);
            actual.setPhoneNumber(phoneNumber);

            verify(user).setUserName(userName);
            verify(user).setPassword(password);
            verify(user).setFullName(fullName);
            verify(user).setStreet(street);
            verify(user).setCity(city);
            verify(user).setState(state);
            verify(user).setZip(zip);
            verify(user).setPhoneNumber(phoneNumber);

            verifyNoMoreInteractions(user);
            verifyNoInteractions(userLoader);
        });
    }

    @Test
    void getRoles_whenFirstAccess_thenFetchRolesWithLoaderAndReturnRoles() {
        assertDoesNotThrow(() -> {

            Set<CassandraRoleProxy> roles = mock(Set.class);
            Set<RoleId> roleIds = mock(Set.class);

            when(user.getCassandraRoleIds()).thenReturn(roleIds);
            when(userLoader.loadRoles(roleIds)).thenReturn(roles);

            CassandraUserProxy actual = new CassandraUserProxy(user, userLoader);

            assertThat(actual.getRoles()).isSameAs(roles);

            verify(user).getCassandraRoleIds();
            verify(userLoader).loadRoles(roleIds);

            verifyNoMoreInteractions(user, userLoader);
        });
    }

    @Test
    void getRoles_whenAlreadyLoaded_thenReturnRolesWithoutLoader() {
        assertDoesNotThrow(() -> {

            Set<CassandraRoleProxy> roles = mock(Set.class);

            CassandraUserProxy actual = new CassandraUserProxy(user, userLoader)
                .setRoles(roles);

            assertThat(actual.getRoles()).isSameAs(roles);

            verify(user, never()).getCassandraRoleIds();

            verifyNoInteractions(userLoader);
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

            CassandraUserProxy actual = new CassandraUserProxy(user, userLoader);

            actual.setRoles(roles);

            assertThat(actual.getRoles()).isSameAs(roles);

            verify(user).setCassandraRoleIds(roleIds);
            verifyNoInteractions(userLoader);
        });
    }

    @Test
    void setRoles_whenGivenListIsNull_thenReplaceWithNewEmptyCollection() {
        assertDoesNotThrow(() -> {

            CassandraUserProxy actual = new CassandraUserProxy(user, userLoader);

            actual.setRoles(null);

            assertThat(actual.getRoles()).isEmpty();

            verify(user).setCassandraRoleIds(any(Set.class));
            verifyNoInteractions(userLoader);
        });
    }
}
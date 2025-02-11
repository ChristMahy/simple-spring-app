package cmahy.webapp.user.adapter.cassandra.entity.proxy;

import cmahy.common.helper.Generator;
import cmahy.webapp.user.adapter.cassandra.entity.domain.CassandraRole;
import cmahy.webapp.user.adapter.cassandra.entity.loader.RoleLoader;
import cmahy.webapp.user.kernel.domain.id.RoleId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CassandraRoleProxyTest {

    @Mock
    private CassandraRole role;

    @Mock
    private RoleLoader roleLoader;

    @Test
    void unwrap() {
        assertDoesNotThrow(() -> {
            assertThat(new CassandraRoleProxy(role, roleLoader).unwrap()).isSameAs(role);
        });
    }

    @Test
    void proxySetters() {
        assertDoesNotThrow(() -> {
            CassandraRoleProxy actual = new CassandraRoleProxy(role, roleLoader);

            String newName = Generator.generateAString();

            actual.setName(newName);

            verify(role).setName(newName);
            verifyNoMoreInteractions(role);
            verifyNoInteractions(roleLoader);
        });
    }

    @Test
    void proxyGetters() {
        assertDoesNotThrow(() -> {
            CassandraRoleProxy actual = new CassandraRoleProxy(role, roleLoader);

            actual.getId();
            actual.getName();

            verify(role).getId();
            verify(role).getName();

            verifyNoMoreInteractions(role);
            verifyNoInteractions(roleLoader);
        });
    }

    @Test
    void usersSetter_whenUsersSetterIsCalled_thenRoleNeverCalled_RoleDoesntOwnReferenceToUSer() {
        assertDoesNotThrow(() -> {
            List<CassandraUserProxy> users = mock(List.class);

            CassandraRoleProxy actual = new CassandraRoleProxy(role, roleLoader);

            actual.setUsers(users);

            verifyNoInteractions(role, roleLoader);
        });
    }

    @Test
    void userGetters_whenUsersAreLoaded_thenReturnUsersWithoutCallLoader() {
        assertDoesNotThrow(() -> {
            List<CassandraUserProxy> users = mock(List.class);

            CassandraRoleProxy actual = new CassandraRoleProxy(role, roleLoader)
                .setUsers(users);

            assertThat(actual.getUsers()).isSameAs(users);

            verifyNoInteractions(role, roleLoader);
        });
    }

    @Test
    void userGetters_whenUsersAreNotLoaded_thenCallLoaderAndReturnUsers() {
        assertDoesNotThrow(() -> {
            List<CassandraUserProxy> users = mock(List.class);

            when(roleLoader.loadUsers(any(RoleId.class))).thenReturn(users);

            CassandraRoleProxy actual = new CassandraRoleProxy(role, roleLoader);

            assertThat(actual.getUsers()).isSameAs(users);

            verify(role).getId();
            verify(roleLoader).loadUsers(any(RoleId.class));
            verifyNoMoreInteractions(role, roleLoader);
        });
    }
}
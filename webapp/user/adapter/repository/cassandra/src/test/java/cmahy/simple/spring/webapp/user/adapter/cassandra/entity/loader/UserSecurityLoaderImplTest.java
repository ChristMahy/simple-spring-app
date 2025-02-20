package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraRoleProxy;
import cmahy.simple.spring.webapp.user.kernel.domain.id.RoleId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserSecurityLoaderImplTest {

    @Mock
    private UserLoader userLoader;

    @InjectMocks
    private UserSecurityLoaderImpl userSecurityLoaderImpl;

    @Test
    void loadRoles() {
        assertDoesNotThrow(() -> {
            Set<RoleId> roleIds = mock(HashSet.class);
            Set<CassandraRoleProxy> roles = mock(HashSet.class);

            when(userLoader.loadRoles(roleIds)).thenReturn(roles);

            Set<CassandraRoleProxy> actual = userSecurityLoaderImpl.loadRoles(roleIds);

            assertThat(actual).isNotNull().isSameAs(roles);
        });
    }
}
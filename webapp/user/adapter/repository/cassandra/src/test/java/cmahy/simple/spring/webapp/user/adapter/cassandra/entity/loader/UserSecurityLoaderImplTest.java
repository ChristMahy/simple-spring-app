package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraRole;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraUserSecurityImpl;
import cmahy.simple.spring.webapp.user.adapter.cassandra.repository.cassandra.CassandraRoleRepositoryImpl;
import cmahy.simple.spring.webapp.user.kernel.domain.id.RoleId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserSecurityLoaderImplTest {

    @Mock
    private CassandraRoleRepositoryImpl cassandraRoleRepository;

    @InjectMocks
    private UserSecurityLoaderImpl userSecurityLoaderImpl;

    @Test
    void entityClass() {
        assertDoesNotThrow(() -> {

            assertThat(userSecurityLoaderImpl.entityClass())
                .isNotNull()
                .isEqualTo(CassandraUserSecurityImpl.class);

        });
    }

    @Test
    void loadRoles() {
        assertDoesNotThrow(() -> {

            Set<RoleId> roleIds = mock(HashSet.class);
            List<CassandraRole> roles = mock(List.class);

            when(cassandraRoleRepository.findAllById(roleIds)).thenReturn(roles);


            Collection<CassandraRole> actual = userSecurityLoaderImpl.loadRoles(roleIds);


            assertThat(actual)
                .isNotNull()
                .isSameAs(roles);

        });
    }
}
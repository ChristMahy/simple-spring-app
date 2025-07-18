package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.*;
import cmahy.simple.spring.webapp.user.adapter.cassandra.repository.cassandra.CassandraRightRepositoryImpl;
import cmahy.simple.spring.webapp.user.adapter.cassandra.repository.cassandra.CassandraUserRepositoryImpl;
import cmahy.simple.spring.webapp.user.kernel.domain.id.RightId;
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
class RoleLoaderImplTest {

    @Mock
    private CassandraUserRepositoryImpl cassandraUserRepository;

    @Mock
    private CassandraRightRepositoryImpl cassandraRightRepository;

    @InjectMocks
    private RoleLoaderImpl roleLoaderImpl;

    @Test
    void entityClass() {
        assertDoesNotThrow(() -> {

            assertThat(roleLoaderImpl.entityClass())
                .isNotNull()
                .isEqualTo(CassandraRole.class);

        });
    }

    @Test
    void loadUsers() {
        assertDoesNotThrow(() -> {

            RoleId roleId = mock(RoleId.class);

            List<CassandraUserImpl> users = mock(List.class);

            when(cassandraUserRepository.findByCassandraRoleId(roleId)).thenReturn(users);


            Collection<CassandraUserImpl> actual = roleLoaderImpl.loadUsers(roleId);


            assertThat(actual)
                .isNotNull()
                .isSameAs(users);

        });
    }

    @Test
    void loadRights() {
        assertDoesNotThrow(() -> {

            Set<RightId> rightIds = mock(Set.class);

            List<CassandraRight> rights = mock(List.class);

            when(cassandraRightRepository.findByRightIds(rightIds)).thenReturn(rights);


            Collection<CassandraRight> actual = roleLoaderImpl.loadRights(rightIds);


            assertThat(actual)
                .isNotNull()
                .isSameAs(rights);

        });
    }
}
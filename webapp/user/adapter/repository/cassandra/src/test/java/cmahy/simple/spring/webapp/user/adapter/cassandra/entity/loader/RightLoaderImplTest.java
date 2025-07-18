package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraRight;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraRole;
import cmahy.simple.spring.webapp.user.adapter.cassandra.repository.cassandra.CassandraRoleRepositoryImpl;
import cmahy.simple.spring.webapp.user.kernel.domain.id.RightId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RightLoaderImplTest {

    @Mock
    private CassandraRoleRepositoryImpl cassandraRoleRepository;

    @InjectMocks
    private RightLoaderImpl rightLoaderImpl;

    @Test
    void entityClass() {
        assertDoesNotThrow(() -> {

            assertThat(rightLoaderImpl.entityClass())
                .isEqualTo(CassandraRight.class);

        });
    }

    @Test
    void loadRoles() {
        assertDoesNotThrow(() -> {

            RightId rightId = mock(RightId.class);

            Set<CassandraRole> roles = mock(Set.class);

            when(cassandraRoleRepository.findAllByRightId(rightId)).thenReturn(roles);


            Collection<CassandraRole> actual = rightLoaderImpl.loadRoles(rightId);


            assertThat(actual)
                .isNotNull()
                .isSameAs(roles);

        });
    }
}
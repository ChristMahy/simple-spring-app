package cmahy.webapp.user.adapter.cassandra.repository.impl;

import cmahy.common.helper.Generator;
import cmahy.webapp.user.adapter.cassandra.entity.domain.CassandraRole;
import cmahy.webapp.user.adapter.cassandra.entity.proxy.CassandraRoleProxy;
import cmahy.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraRoleProxyFactory;
import cmahy.webapp.user.adapter.cassandra.repository.cassandra.CassandraRoleRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleRepositoryImplTest {

    @Mock
    private CassandraRoleRepositoryImpl cassandraRoleRepository;

    @Mock
    private CassandraRoleProxyFactory cassandraRoleProxyFactory;

    @InjectMocks
    private RoleRepositoryImpl roleRepositoryImpl;

    @Test
    void findByName() {
        assertDoesNotThrow(() -> {
            String aName = Generator.generateAString();
            CassandraRole role = mock(CassandraRole.class);
            CassandraRoleProxy roleProxy = mock(CassandraRoleProxy.class);

            when(cassandraRoleRepository.findByName(aName)).thenReturn(Optional.of(role));
            when(cassandraRoleProxyFactory.create(role)).thenReturn(roleProxy);

            Optional<CassandraRoleProxy> actual = roleRepositoryImpl.findByName(aName);

            assertThat(actual)
                .isNotEmpty()
                .hasValue(roleProxy);
        });
    }

    @Test
    void findByName_whenRoleNotFound_thenReturnEmptyResult() {
        assertDoesNotThrow(() -> {
            String aName = Generator.generateAString();

            when(cassandraRoleRepository.findByName(aName)).thenReturn(Optional.empty());

            Optional<CassandraRoleProxy> actual = roleRepositoryImpl.findByName(aName);

            assertThat(actual).isEmpty();

            verifyNoInteractions(cassandraRoleProxyFactory);
        });
    }

    @Test
    void save() {
        assertDoesNotThrow(() -> {
            UUID roleId = Generator.randomUUID();

            CassandraRole inRole = mock(CassandraRole.class);
            when(inRole.getId()).thenReturn(roleId);

            CassandraRoleProxy inRoleProxy = mock(CassandraRoleProxy.class);
            when(inRoleProxy.unwrap()).thenReturn(inRole);

            CassandraRole outRole = mock(CassandraRole.class);
            CassandraRoleProxy outRoleProxy = mock(CassandraRoleProxy.class);

            when(cassandraRoleRepository.save(inRole)).thenReturn(outRole);
            when(cassandraRoleProxyFactory.create(outRole)).thenReturn(outRoleProxy);

            CassandraRoleProxy actual = roleRepositoryImpl.save(inRoleProxy);

            assertThat(actual)
                .isNotNull()
                .isSameAs(outRoleProxy);

            verify(inRole, never()).setId(any(UUID.class));
        });
    }

    @Test
    void save_whenMissingId_thenGenerateNewOne() {
        assertDoesNotThrow(() -> {
            try (MockedStatic<UUID> uuidMock = Mockito.mockStatic(UUID.class)) {
                UUID roleId = mock(UUID.class);

                uuidMock.when(() -> UUID.randomUUID()).thenReturn(roleId);

                CassandraRole inRole = mock(CassandraRole.class);

                CassandraRoleProxy inRoleProxy = mock(CassandraRoleProxy.class);
                when(inRoleProxy.unwrap()).thenReturn(inRole);

                CassandraRole outRole = mock(CassandraRole.class);
                CassandraRoleProxy outRoleProxy = mock(CassandraRoleProxy.class);

                when(cassandraRoleRepository.save(inRole)).thenReturn(outRole);
                when(cassandraRoleProxyFactory.create(outRole)).thenReturn(outRoleProxy);

                CassandraRoleProxy actual = roleRepositoryImpl.save(inRoleProxy);

                assertThat(actual)
                    .isNotNull()
                    .isSameAs(outRoleProxy);

                verify(inRole).setId(roleId);
            }
        });
    }
}
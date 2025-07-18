package cmahy.simple.spring.webapp.user.adapter.cassandra.repository.impl;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraUserSecurityImpl;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraUserSecurityProxy;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraUserSecurityProxyFactory;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.provider.CassandraUserProxyFactoryProvider;
import cmahy.simple.spring.webapp.user.adapter.cassandra.repository.cassandra.CassandraUserSecurityRepositoryImpl;
import cmahy.simple.spring.webapp.user.kernel.domain.AuthProvider;
import org.junit.jupiter.api.BeforeEach;
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
class UserSecurityRepositoryImplTest {

    @Mock
    private CassandraUserSecurityRepositoryImpl userSecurityRepository;

    @Mock
    private CassandraUserProxyFactoryProvider proxyFactoryResolver;

    @InjectMocks
    private UserSecurityRepositoryImpl userSecurityRepositoryImpl;

    @Mock
    private CassandraUserSecurityProxyFactory userSecurityProxyFactory;

    @BeforeEach
    void setUp() {
        when(proxyFactoryResolver.resolve(CassandraUserSecurityImpl.class)).thenReturn(userSecurityProxyFactory);
    }

    @Test
    void findByUserNameAndAuthProvider() {
        assertDoesNotThrow(() -> {
            String userName = Generator.generateAString();
            AuthProvider authProvider = Generator.randomEnum(AuthProvider.class);

            CassandraUserSecurityImpl user = mock(CassandraUserSecurityImpl.class);
            CassandraUserSecurityProxy userProxy = mock(CassandraUserSecurityProxy.class);

            when(userSecurityRepository.findByUserNameAndAuthProvider(userName, authProvider)).thenReturn(Optional.of(user));
            when(userSecurityProxyFactory.create(user)).thenReturn(userProxy);

            Optional<CassandraUserSecurityProxy> actual = userSecurityRepositoryImpl.findByUserNameAndAuthProvider(userName, authProvider);

            assertThat(actual)
                .isNotEmpty()
                .hasValue(userProxy);
        });
    }

    @Test
    void findByUserNameAndAuthProvider_whenNotFound_thenReturnEmptyResult() {
        assertDoesNotThrow(() -> {
            String userName = Generator.generateAString();
            AuthProvider authProvider = Generator.randomEnum(AuthProvider.class);

            when(userSecurityRepository.findByUserNameAndAuthProvider(userName, authProvider)).thenReturn(Optional.empty());

            Optional<CassandraUserSecurityProxy> actual = userSecurityRepositoryImpl.findByUserNameAndAuthProvider(userName, authProvider);

            assertThat(actual).isEmpty();

            verifyNoInteractions(userSecurityProxyFactory);
        });
    }

    @Test
    void save() {
        assertDoesNotThrow(() -> {
            UUID userId = Generator.randomUUID();

            CassandraUserSecurityImpl inUser = mock(CassandraUserSecurityImpl.class);
            when(inUser.getId()).thenReturn(userId);

            CassandraUserSecurityProxy inUserProxy = mock(CassandraUserSecurityProxy.class);
            when(inUserProxy.unwrap()).thenReturn(inUser);

            CassandraUserSecurityImpl outUser = mock(CassandraUserSecurityImpl.class);
            CassandraUserSecurityProxy outProxy = mock(CassandraUserSecurityProxy.class);

            when(userSecurityRepository.save(inUser)).thenReturn(outUser);
            when(userSecurityProxyFactory.create(outUser)).thenReturn(outProxy);

            CassandraUserSecurityProxy actual = userSecurityRepositoryImpl.save(inUserProxy);

            assertThat(actual)
                .isNotNull()
                .isSameAs(outProxy);

            verify(inUser, never()).setId(any(UUID.class));
        });
    }

    @Test
    void save_whenMissingId_thenGenerateNewOne() {
        assertDoesNotThrow(() -> {
            try (MockedStatic<UUID> uuidMock = Mockito.mockStatic(UUID.class)) {
                UUID userId = mock(UUID.class);

                uuidMock.when(() -> UUID.randomUUID()).thenReturn(userId);

                CassandraUserSecurityImpl inUser = mock(CassandraUserSecurityImpl.class, RETURNS_SELF);

                CassandraUserSecurityProxy inUserProxy = mock(CassandraUserSecurityProxy.class);
                when(inUserProxy.unwrap()).thenReturn(inUser);

                CassandraUserSecurityImpl outUser = mock(CassandraUserSecurityImpl.class);
                CassandraUserSecurityProxy outProxy = mock(CassandraUserSecurityProxy.class);

                when(userSecurityRepository.save(inUser)).thenReturn(outUser);
                when(userSecurityProxyFactory.create(outUser)).thenReturn(outProxy);

                CassandraUserSecurityProxy actual = userSecurityRepositoryImpl.save(inUserProxy);

                assertThat(actual)
                    .isNotNull()
                    .isSameAs(outProxy);

                verify(inUser).setId(any(UUID.class));
            }
        });
    }
}
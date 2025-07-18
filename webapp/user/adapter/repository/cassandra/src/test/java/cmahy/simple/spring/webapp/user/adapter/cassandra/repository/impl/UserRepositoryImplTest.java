package cmahy.simple.spring.webapp.user.adapter.cassandra.repository.impl;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraUserImpl;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraUserProxy;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraUserProxyFactory;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.provider.CassandraUserProxyFactoryProvider;
import cmahy.simple.spring.webapp.user.adapter.cassandra.repository.cassandra.CassandraUserRepositoryImpl;
import cmahy.simple.spring.webapp.user.kernel.domain.id.UserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryImplTest {

    @Mock
    private CassandraUserRepositoryImpl cassandraUserRepository;

    @Mock
    private CassandraUserProxyFactoryProvider proxyFactoryResolver;

    @InjectMocks
    private UserRepositoryImpl userRepositoryImpl;

    @Mock
    private CassandraUserProxyFactory cassandraUserProxyFactory;

    @BeforeEach
    void setUp() {
        when(proxyFactoryResolver.resolve(CassandraUserImpl.class)).thenReturn(cassandraUserProxyFactory);
    }

    @Test
    void findById() {
        assertDoesNotThrow(() -> {
            UserId userId = mock(UserId.class);

            CassandraUserImpl user = mock(CassandraUserImpl.class);
            CassandraUserProxy userProxy = mock(CassandraUserProxy.class);

            when(cassandraUserRepository.findById(userId)).thenReturn(Optional.of(user));
            when(cassandraUserProxyFactory.create(user)).thenReturn(userProxy);

            Optional<CassandraUserProxy> actual = userRepositoryImpl.findById(userId);

            assertThat(actual)
                .isNotEmpty()
                .hasValue(userProxy);
        });
    }

    @Test
    void findById_whenNotFound_thenReturnEmptyResult() {
        assertDoesNotThrow(() -> {
            UserId userId = mock(UserId.class);

            when(cassandraUserRepository.findById(userId)).thenReturn(Optional.empty());

            Optional<CassandraUserProxy> actual = userRepositoryImpl.findById(userId);

            assertThat(actual).isEmpty();

            verifyNoInteractions(cassandraUserProxyFactory);
        });
    }

    @Test
    void findByUserName() {
        assertDoesNotThrow(() -> {
            String userName = Generator.generateAString();

            CassandraUserImpl user = mock(CassandraUserImpl.class);
            CassandraUserProxy userProxy = mock(CassandraUserProxy.class);

            when(cassandraUserRepository.findByUserName(userName)).thenReturn(Optional.of(user));
            when(cassandraUserProxyFactory.create(user)).thenReturn(userProxy);

            Optional<CassandraUserProxy> actual = userRepositoryImpl.findByUserName(userName);

            assertThat(actual)
                .isNotEmpty()
                .hasValue(userProxy);
        });
    }

    @Test
    void findByUserName_whenNotFound_thenReturnEmptyResult() {
        assertDoesNotThrow(() -> {
            String userName = Generator.generateAString();

            when(cassandraUserRepository.findByUserName(userName)).thenReturn(Optional.empty());

            Optional<CassandraUserProxy> actual = userRepositoryImpl.findByUserName(userName);

            assertThat(actual).isEmpty();

            verifyNoInteractions(cassandraUserProxyFactory);
        });
    }
}
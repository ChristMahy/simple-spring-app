package cmahy.simple.spring.webapp.user.adapter.cassandra.repository.impl;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraRight;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraRightProxy;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraRightProxyFactory;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.provider.CassandraUserProxyFactoryProvider;
import cmahy.simple.spring.webapp.user.adapter.cassandra.repository.cassandra.CassandraRightRepositoryImpl;
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
class RightRepositoryImplTest {

    @Mock
    private CassandraRightRepositoryImpl cassandraRightRepository;

    @Mock
    private CassandraUserProxyFactoryProvider proxyFactoryResolver;

    @InjectMocks
    private RightRepositoryImpl rightRepositoryImpl;

    @Mock
    private CassandraRightProxyFactory cassandraRightProxyFactory;

    @BeforeEach
    void setUp() {
        when(proxyFactoryResolver.resolve(CassandraRight.class)).thenReturn(cassandraRightProxyFactory);
    }

    @Test
    void findByName() {
        assertDoesNotThrow(() -> {

            String aName = Generator.generateAString();
            CassandraRight right = mock(CassandraRight.class);
            CassandraRightProxy rightProxy = mock(CassandraRightProxy.class);

            when(cassandraRightRepository.findByName(aName)).thenReturn(Optional.of(right));
            when(cassandraRightProxyFactory.create(right)).thenReturn(rightProxy);


            Optional<CassandraRightProxy> actual = rightRepositoryImpl.findByName(aName);


            assertThat(actual)
                .isNotEmpty()
                .hasValue(rightProxy);
        });
    }

    @Test
    void findByName_whenRightNotFound_thenReturnEmptyResult() {
        assertDoesNotThrow(() -> {

            String aName = Generator.generateAString();

            when(cassandraRightRepository.findByName(aName)).thenReturn(Optional.empty());


            Optional<CassandraRightProxy> actual = rightRepositoryImpl.findByName(aName);


            assertThat(actual).isEmpty();

            verifyNoInteractions(cassandraRightProxyFactory);

        });
    }

    @Test
    void save() {
        assertDoesNotThrow(() -> {

            UUID roleId = Generator.randomUUID();

            CassandraRight inRight = mock(CassandraRight.class);
            when(inRight.getId()).thenReturn(roleId);

            CassandraRightProxy inRightProxy = mock(CassandraRightProxy.class);
            when(inRightProxy.unwrap()).thenReturn(inRight);

            CassandraRight outRight = mock(CassandraRight.class);
            CassandraRightProxy outRightProxy = mock(CassandraRightProxy.class);

            when(cassandraRightRepository.save(inRight)).thenReturn(outRight);
            when(cassandraRightProxyFactory.create(outRight)).thenReturn(outRightProxy);


            CassandraRightProxy actual = rightRepositoryImpl.save(inRightProxy);


            assertThat(actual)
                .isNotNull()
                .isSameAs(outRightProxy);

            verify(inRight, never()).setId(any(UUID.class));

        });
    }

    @Test
    void save_whenMissingId_thenGenerateNewOne() {
        assertDoesNotThrow(() -> {

            try (MockedStatic<UUID> uuidMock = Mockito.mockStatic(UUID.class)) {

                UUID rightId = mock(UUID.class);

                uuidMock.when(UUID::randomUUID).thenReturn(rightId);

                CassandraRight inRight = mock(CassandraRight.class);

                CassandraRightProxy inRightProxy = mock(CassandraRightProxy.class);
                when(inRightProxy.unwrap()).thenReturn(inRight);

                CassandraRight outRight = mock(CassandraRight.class);
                CassandraRightProxy outRightProxy = mock(CassandraRightProxy.class);

                when(cassandraRightRepository.save(inRight)).thenReturn(outRight);
                when(cassandraRightProxyFactory.create(outRight)).thenReturn(outRightProxy);


                CassandraRightProxy actual = rightRepositoryImpl.save(inRightProxy);


                assertThat(actual)
                    .isNotNull()
                    .isSameAs(outRightProxy);

                verify(inRight).setId(rightId);

            }

        });
    }

}
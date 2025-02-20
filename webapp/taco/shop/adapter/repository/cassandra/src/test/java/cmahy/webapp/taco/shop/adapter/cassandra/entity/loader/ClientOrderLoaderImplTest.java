package cmahy.webapp.taco.shop.adapter.cassandra.entity.loader;

import cmahy.common.helper.Generator;
import cmahy.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraTaco;
import cmahy.webapp.taco.shop.adapter.cassandra.entity.proxy.CassandraTacoProxy;
import cmahy.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.CassandraTacoProxyFactory;
import cmahy.webapp.taco.shop.adapter.cassandra.repository.cassandra.CassandraTacoRepository;
import cmahy.webapp.taco.shop.kernel.domain.id.TacoId;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraUserImpl;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraUserProxy;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraUserProxyFactory;
import cmahy.simple.spring.webapp.user.adapter.cassandra.repository.cassandra.CassandraUserRepositoryImpl;
import cmahy.webapp.user.kernel.domain.id.UserId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientOrderLoaderImplTest {

    @Mock
    private CassandraTacoRepository tacoRepository;
    @Mock
    private CassandraUserRepositoryImpl userRepository;

    @Mock
    private CassandraTacoProxyFactory tacoProxyFactory;
    @Mock
    private CassandraUserProxyFactory userProxyFactory;

    @InjectMocks
    private ClientOrderLoaderImpl clientOrderLoaderImpl;

    @Test
    void loadTacos() {
        assertDoesNotThrow(() -> {
            List<TacoId> tacoIds = mock(List.class);

            List<CassandraTacoProxy> tacoProxies = new ArrayList<>();

            List<CassandraTaco> tacos = Stream
                .generate(() -> {
                    CassandraTaco taco = mock(CassandraTaco.class);
                    CassandraTacoProxy tacoProxy = mock(CassandraTacoProxy.class);

                    when(tacoProxyFactory.create(taco)).thenReturn(tacoProxy);

                    tacoProxies.add(tacoProxy);

                    return taco;
                })
                .limit(Generator.randomInt(100, 1000))
                .toList();

            when(tacoRepository.findAllById(tacoIds)).thenReturn(tacos);

            List<CassandraTacoProxy> actual = clientOrderLoaderImpl.loadTacos(tacoIds);

            assertThat(actual)
                .isNotNull()
                .containsExactlyInAnyOrderElementsOf(tacoProxies);
        });
    }

    @Test
    void loadUser() {
        assertDoesNotThrow(() -> {
            UserId id = mock(UserId.class);

            CassandraUserImpl user = mock(CassandraUserImpl.class);
            CassandraUserProxy userProxy = mock(CassandraUserProxy.class);

            when(userRepository.findById(id)).thenReturn(Optional.of(user));
            when(userProxyFactory.create(user)).thenReturn(userProxy);

            Optional<CassandraUserProxy> actual = clientOrderLoaderImpl.loadUser(id);

            assertThat(actual)
                .isNotEmpty()
                .hasValueSatisfying(value -> {
                    assertThat(value).isSameAs(userProxy);
                });
        });
    }
}
package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.loader;

import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraTaco;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.repository.cassandra.CassandraTacoRepository;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.id.TacoId;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraUserImpl;
import cmahy.simple.spring.webapp.user.adapter.cassandra.repository.cassandra.CassandraUserRepositoryImpl;
import cmahy.simple.spring.webapp.user.kernel.domain.id.UserId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

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

    @InjectMocks
    private ClientOrderLoaderImpl clientOrderLoaderImpl;

    @Test
    void loadTacos() {
        assertDoesNotThrow(() -> {
            List<TacoId> tacoIds = mock(List.class);
            List<CassandraTaco> tacos = mock(List.class);

            when(tacoRepository.findAllById(tacoIds)).thenReturn(tacos);

            List<CassandraTaco> actual = clientOrderLoaderImpl.loadTacos(tacoIds);

            assertThat(actual)
                .isNotNull()
                .isSameAs(tacos);
        });
    }

    @Test
    void loadUser() {
        assertDoesNotThrow(() -> {
            UserId id = mock(UserId.class);

            CassandraUserImpl user = mock(CassandraUserImpl.class);

            when(userRepository.findById(id)).thenReturn(Optional.of(user));

            Optional<CassandraUserImpl> actual = clientOrderLoaderImpl.loadUser(id);

            assertThat(actual)
                .isNotEmpty()
                .hasValueSatisfying(value -> {
                    assertThat(value).isSameAs(user);
                });
        });
    }
}
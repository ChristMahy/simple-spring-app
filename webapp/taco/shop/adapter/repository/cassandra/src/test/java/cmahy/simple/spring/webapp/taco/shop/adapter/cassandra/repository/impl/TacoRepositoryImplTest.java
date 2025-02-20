package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.repository.impl;

import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraTaco;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.CassandraTacoProxy;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.CassandraTacoProxyFactory;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.repository.cassandra.CassandraTacoRepository;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.id.IngredientId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TacoRepositoryImplTest {

    @Mock
    private CassandraTacoRepository tacoRepository;

    @Mock
    private CassandraTacoProxyFactory tacoProxyFactory;

    @InjectMocks
    private TacoRepositoryImpl tacoRepositoryImpl;

    @Test
    void save() {
        assertDoesNotThrow(() -> {

            UUID tacoId = mock(UUID.class);
            Date createdDate = mock(Date.class);

            CassandraTaco inTaco = mock(CassandraTaco.class);
            when(inTaco.getId()).thenReturn(tacoId);
            when(inTaco.getCreatedAt()).thenReturn(createdDate);

            CassandraTacoProxy inTacoProxy = mock(CassandraTacoProxy.class);
            when(inTacoProxy.unwrap()).thenReturn(inTaco);

            CassandraTaco outTaco = mock(CassandraTaco.class);
            CassandraTacoProxy outTacoProxy = mock(CassandraTacoProxy.class);

            when(tacoRepository.save(inTaco)).thenReturn(outTaco);
            when(tacoProxyFactory.create(outTaco)).thenReturn(outTacoProxy);

            CassandraTacoProxy actual = tacoRepositoryImpl.save(inTacoProxy);

            assertThat(actual)
                .isNotNull()
                .isSameAs(outTacoProxy);

            verify(inTaco, never()).setId(any(UUID.class));
            verify(inTaco, never()).setCreatedAt(any(Date.class));
        });
    }

    @Test
    void save_whenIdIsNull_thenCreateNewOne() {
        assertDoesNotThrow(() -> {

            try (MockedStatic<UUID> uuidMock = Mockito.mockStatic(UUID.class)) {
                UUID tacoId = mock(UUID.class);

                uuidMock.when(() -> UUID.randomUUID()).thenReturn(tacoId);

                Date createdDate = mock(Date.class);

                CassandraTaco inTaco = mock(CassandraTaco.class);
                when(inTaco.getCreatedAt()).thenReturn(createdDate);

                CassandraTacoProxy inTacoProxy = mock(CassandraTacoProxy.class);
                when(inTacoProxy.unwrap()).thenReturn(inTaco);

                CassandraTaco outTaco = mock(CassandraTaco.class);
                CassandraTacoProxy outTacoProxy = mock(CassandraTacoProxy.class);

                when(tacoRepository.save(inTaco)).thenReturn(outTaco);
                when(tacoProxyFactory.create(outTaco)).thenReturn(outTacoProxy);

                CassandraTacoProxy actual = tacoRepositoryImpl.save(inTacoProxy);

                assertThat(actual)
                    .isNotNull()
                    .isSameAs(outTacoProxy);

                verify(inTaco).setId(tacoId);
            }
        });
    }

    @Test
    void save_whenCreatedAtIsNull_thenFillWithNewDate() {
        assertDoesNotThrow(() -> {

            UUID tacoId = mock(UUID.class);

            CassandraTaco inTaco = mock(CassandraTaco.class);
            when(inTaco.getId()).thenReturn(tacoId);

            CassandraTacoProxy inTacoProxy = mock(CassandraTacoProxy.class);
            when(inTacoProxy.unwrap()).thenReturn(inTaco);

            CassandraTaco outTaco = mock(CassandraTaco.class);
            CassandraTacoProxy outTacoProxy = mock(CassandraTacoProxy.class);

            when(tacoRepository.save(inTaco)).thenReturn(outTaco);
            when(tacoProxyFactory.create(outTaco)).thenReturn(outTacoProxy);

            CassandraTacoProxy actual = tacoRepositoryImpl.save(inTacoProxy);

            assertThat(actual)
                .isNotNull()
                .isSameAs(outTacoProxy);

            verify(inTaco).setCreatedAt(any(Date.class));
        });
    }

    @Test
    void findByIngredientId() {
        assertDoesNotThrow(() -> {
            IngredientId ingredientId = mock(IngredientId.class);

            Set<CassandraTaco> tacos = mock(Set.class);
            Set<CassandraTacoProxy> tacoProxies = mock(Set.class);

            Stream<CassandraTaco> tacoStream = mock(Stream.class);
            Stream<CassandraTacoProxy> tacoProxyStream = mock(Stream.class);

            when(tacos.stream()).thenReturn(tacoStream);
            when(tacoStream.map(any(Function.class))).thenReturn(tacoProxyStream);
            when(tacoProxyStream.collect(any(Collector.class))).thenReturn(tacoProxies);

            when(tacoRepository.findByIngredientId(ingredientId)).thenReturn(tacos);

            Set<CassandraTacoProxy> actual = tacoRepositoryImpl.findByIngredientId(ingredientId);

            assertThat(actual)
                .isNotNull()
                .isSameAs(tacoProxies);
        });
    }
}
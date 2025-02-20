package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.repository.impl;

import cmahy.simple.spring.common.entity.page.EntityPageable;
import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraClientOrder;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.CassandraClientOrderProxy;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.CassandraClientOrderProxyFactory;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.repository.cassandra.CassandraClientOrderRepository;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.page.ClientOrderPage;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraUserProxy;
import cmahy.simple.spring.webapp.user.kernel.domain.id.UserId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.*;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientOrderRepositoryImplTest {

    @Mock
    private CassandraClientOrderRepository clientOrderRepository;

    @Mock
    private CassandraClientOrderProxyFactory clientOrderProxyFactory;

    @InjectMocks
    private ClientOrderRepositoryImpl clientOrderRepositoryImpl;

    @Test
    void getByUser() {
        assertDoesNotThrow(() -> {

            CassandraUserProxy user = mock(CassandraUserProxy.class);
            EntityPageable pageable = mock(EntityPageable.class);

            when(pageable.pageSize()).thenReturn(Generator.randomInt(1, Integer.MAX_VALUE));
            when(pageable.pageNumber()).thenReturn(Generator.randomInt(1, Integer.MAX_VALUE));

            Slice<CassandraClientOrder> slice = mock(Slice.class);
            Slice<CassandraClientOrderProxy> sliceProxy = mock(Slice.class);

            when(slice.map(any(Function.class))).thenReturn(sliceProxy);

            List<CassandraClientOrderProxy> clientOrderProxies = mock(List.class);
            Integer elementsSize = Generator.randomIntEqualOrAboveZero();

            when(sliceProxy.getContent()).thenReturn(clientOrderProxies);
            when(sliceProxy.getNumberOfElements()).thenReturn(elementsSize);

            when(clientOrderRepository.findByUserId(any(UserId.class), any(Pageable.class))).thenReturn(slice);

            ClientOrderPage<CassandraClientOrderProxy> actual = clientOrderRepositoryImpl.getByUser(user, pageable);

            assertThat(actual).isNotNull();
            assertThat(actual.content()).isNotNull().isSameAs(clientOrderProxies);
            assertThat(actual.totalElements()).isNotNull().isEqualTo(elementsSize.longValue());

            verifyNoInteractions(clientOrderProxyFactory);
        });
    }

    @Test
    void save() {
        assertDoesNotThrow(() -> {

            UUID clientOrderId = mock(UUID.class);
            Date placedAt = mock(Date.class);

            CassandraClientOrder inClientOrder = mock(CassandraClientOrder.class);
            when(inClientOrder.getId()).thenReturn(clientOrderId);
            when(inClientOrder.getPlacedAt()).thenReturn(placedAt);

            CassandraClientOrderProxy inClientOrderProxy = mock(CassandraClientOrderProxy.class);
            when(inClientOrderProxy.unwrap()).thenReturn(inClientOrder);

            CassandraClientOrder outClientOrder = mock(CassandraClientOrder.class);
            CassandraClientOrderProxy outClientOrderProxy = mock(CassandraClientOrderProxy.class);

            when(clientOrderRepository.save(inClientOrder)).thenReturn(outClientOrder);
            when(clientOrderProxyFactory.create(outClientOrder)).thenReturn(outClientOrderProxy);

            CassandraClientOrderProxy actual = clientOrderRepositoryImpl.save(inClientOrderProxy);

            assertThat(actual)
                .isNotNull()
                .isSameAs(outClientOrderProxy);

            verify(inClientOrder, never()).setId(any(UUID.class));
            verify(inClientOrder, never()).setPlacedAt(any(Date.class));
        });
    }

    @Test
    void save_whenIdIsNull_thenGenerateNewOne() {
        assertDoesNotThrow(() -> {

            try (MockedStatic<UUID> uuidMock = Mockito.mockStatic(UUID.class)) {
                UUID clientOrderId = mock(UUID.class);

                uuidMock.when(() -> UUID.randomUUID()).thenReturn(clientOrderId);

                Date placedAt = mock(Date.class);

                CassandraClientOrder inClientOrder = mock(CassandraClientOrder.class);
                when(inClientOrder.getPlacedAt()).thenReturn(placedAt);

                CassandraClientOrderProxy inClientOrderProxy = mock(CassandraClientOrderProxy.class);
                when(inClientOrderProxy.unwrap()).thenReturn(inClientOrder);

                CassandraClientOrder outClientOrder = mock(CassandraClientOrder.class);
                CassandraClientOrderProxy outClientOrderProxy = mock(CassandraClientOrderProxy.class);

                when(clientOrderRepository.save(inClientOrder)).thenReturn(outClientOrder);
                when(clientOrderProxyFactory.create(outClientOrder)).thenReturn(outClientOrderProxy);

                CassandraClientOrderProxy actual = clientOrderRepositoryImpl.save(inClientOrderProxy);

                assertThat(actual)
                    .isNotNull()
                    .isSameAs(outClientOrderProxy);

                verify(inClientOrder).setId(clientOrderId);
            }
        });
    }

    @Test
    void save_whenPlacedAtIsNull_thenSetWithNewDate() {
        assertDoesNotThrow(() -> {

            UUID clientOrderId = mock(UUID.class);

            CassandraClientOrder inClientOrder = mock(CassandraClientOrder.class);
            when(inClientOrder.getId()).thenReturn(clientOrderId);

            CassandraClientOrderProxy inClientOrderProxy = mock(CassandraClientOrderProxy.class);
            when(inClientOrderProxy.unwrap()).thenReturn(inClientOrder);

            CassandraClientOrder outClientOrder = mock(CassandraClientOrder.class);
            CassandraClientOrderProxy outClientOrderProxy = mock(CassandraClientOrderProxy.class);

            when(clientOrderRepository.save(inClientOrder)).thenReturn(outClientOrder);
            when(clientOrderProxyFactory.create(outClientOrder)).thenReturn(outClientOrderProxy);

            CassandraClientOrderProxy actual = clientOrderRepositoryImpl.save(inClientOrderProxy);

            assertThat(actual)
                .isNotNull()
                .isSameAs(outClientOrderProxy);

            verify(inClientOrder).setPlacedAt(any(Date.class));
        });
    }
}
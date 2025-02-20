package cmahy.webapp.taco.shop.adapter.cassandra.entity.proxy;

import cmahy.common.helper.Generator;
import cmahy.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraClientOrder;
import cmahy.webapp.taco.shop.adapter.cassandra.entity.loader.ClientOrderLoader;
import cmahy.webapp.taco.shop.kernel.domain.id.TacoId;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraUserProxy;
import cmahy.webapp.user.kernel.domain.id.UserId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CassandraClientOrderProxyTest {

    @Mock
    private CassandraClientOrder clientOrder;

    @Mock
    private ClientOrderLoader clientOrderLoader;

    @Test
    void unwrap() {
        assertDoesNotThrow(() -> {
            assertThat(new CassandraClientOrderProxy(clientOrder, clientOrderLoader).unwrap()).isSameAs(clientOrder);
        });
    }

    @Test
    void setters() {
        assertDoesNotThrow(() -> {

            CassandraClientOrderProxy actual = new CassandraClientOrderProxy(clientOrder, clientOrderLoader);

            String deliveryName = Generator.generateAString();
            String deliveryStreet = Generator.generateAString();
            String deliveryCity = Generator.generateAString();
            String deliveryState = Generator.generateAString();
            String deliveryZip = Generator.generateAString();

            String ccNumber = Generator.generateAString();
            String ccExpiration = Generator.generateAString();
            String ccCVV = Generator.generateAString();

            actual.setDeliveryName(deliveryName);
            actual.setDeliveryStreet(deliveryStreet);
            actual.setDeliveryCity(deliveryCity);
            actual.setDeliveryState(deliveryState);
            actual.setDeliveryZip(deliveryZip);

            actual.setCcNumber(ccNumber);
            actual.setCcExpiration(ccExpiration);
            actual.setCcCVV(ccCVV);

            verify(clientOrder).setDeliveryName(deliveryName);
            verify(clientOrder).setDeliveryStreet(deliveryStreet);
            verify(clientOrder).setDeliveryCity(deliveryCity);
            verify(clientOrder).setDeliveryState(deliveryState);
            verify(clientOrder).setDeliveryZip(deliveryZip);

            verify(clientOrder).setCcNumber(ccNumber);
            verify(clientOrder).setCcExpiration(ccExpiration);
            verify(clientOrder).setCcCVV(ccCVV);

            verify(clientOrder, never()).setId(any(UUID.class));
            verify(clientOrder, never()).setPlacedAt(any(Date.class));

            verifyNoMoreInteractions(clientOrder);
            verifyNoInteractions(clientOrderLoader);
        });
    }

    @Test
    void getters() {
        assertDoesNotThrow(() -> {

            CassandraClientOrderProxy actual = new CassandraClientOrderProxy(clientOrder, clientOrderLoader);

            actual.getId();
            actual.getPlacedAt();
            actual.getDeliveryName();
            actual.getDeliveryStreet();
            actual.getDeliveryCity();
            actual.getDeliveryState();
            actual.getDeliveryZip();
            actual.getCcNumber();
            actual.getCcExpiration();
            actual.getCcCVV();

            verify(clientOrder).getId();
            verify(clientOrder).getPlacedAt();
            verify(clientOrder).getDeliveryName();
            verify(clientOrder).getDeliveryStreet();
            verify(clientOrder).getDeliveryCity();
            verify(clientOrder).getDeliveryState();
            verify(clientOrder).getDeliveryZip();
            verify(clientOrder).getCcNumber();
            verify(clientOrder).getCcExpiration();
            verify(clientOrder).getCcCVV();

            verifyNoMoreInteractions(clientOrder);
            verifyNoInteractions(clientOrderLoader);
        });
    }

    @Test
    void getUser_whenFirstAccess_thenFetchUserWithLoaderAndReturnUser() {
        assertDoesNotThrow(() -> {

            CassandraUserProxy user = mock(CassandraUserProxy.class);
            UserId userId = mock(UserId.class);

            when(clientOrder.getUserId()).thenReturn(userId);
            when(clientOrderLoader.loadUser(userId)).thenReturn(Optional.of(user));

            CassandraClientOrderProxy actual = new CassandraClientOrderProxy(clientOrder, clientOrderLoader);

            assertThat(actual.getUser())
                .isSameAs(user);

            verify(clientOrder).getUserId();
            verify(clientOrderLoader).loadUser(userId);

            verifyNoMoreInteractions(clientOrderLoader);
        });
    }

    @Test
    void getUser_whenAlreadyLoaded_thenReturnUserWithoutLoader() {
        assertDoesNotThrow(() -> {

            CassandraUserProxy user = mock(CassandraUserProxy.class);

            CassandraClientOrderProxy actual = new CassandraClientOrderProxy(clientOrder, clientOrderLoader)
                .setUser(user);

            assertThat(actual.getUser()).isSameAs(user);

            verify(clientOrder, never()).getUserId();

            verifyNoInteractions(clientOrderLoader);
        });
    }

    @Test
    void setUser() {
        assertDoesNotThrow(() -> {

            CassandraUserProxy user = mock(CassandraUserProxy.class);
            UUID userId = mock(UUID.class);

            when(user.getId()).thenReturn(userId);

            CassandraClientOrderProxy actual = new CassandraClientOrderProxy(clientOrder, clientOrderLoader);

            actual.setUser(user);

            assertThat(actual.getUser()).isSameAs(user);

            verify(clientOrder).setUserId(any(UserId.class));
            verifyNoInteractions(clientOrderLoader);
        });
    }

    @Test
    void setUser_whenGivenUserIsNull_thenReplaceWithEmptyUser() {
        assertDoesNotThrow(() -> {

            CassandraClientOrderProxy actual = new CassandraClientOrderProxy(clientOrder, clientOrderLoader);

            actual.setUser(null);

            verify(clientOrder).setUserId(null);
        });
    }

    @Test
    void getTacos_whenFirstAccess_thenFetchTacosWithLoaderAndReturnTacos() {
        assertDoesNotThrow(() -> {

            List<CassandraTacoProxy> tacos = mock(List.class);
            List<TacoId> tacoIds = mock(List.class);

            when(clientOrder.getTacoIds()).thenReturn(tacoIds);
            when(clientOrderLoader.loadTacos(tacoIds)).thenReturn(tacos);

            CassandraClientOrderProxy actual = new CassandraClientOrderProxy(clientOrder, clientOrderLoader);

            assertThat(actual.getTacos()).isSameAs(tacos);

            verify(clientOrder).getTacoIds();
            verify(clientOrderLoader).loadTacos(tacoIds);

            verifyNoMoreInteractions(clientOrderLoader);
        });
    }

    @Test
    void getTacos_whenAlreadyLoaded_thenReturnTacosWithoutLoader() {
        assertDoesNotThrow(() -> {

            List<CassandraTacoProxy> tacos = mock(List.class);

            CassandraClientOrderProxy actual = new CassandraClientOrderProxy(clientOrder, clientOrderLoader)
                .setTacos(tacos);

            assertThat(actual.getTacos()).isSameAs(tacos);

            verify(clientOrder, never()).getTacoIds();

            verifyNoInteractions(clientOrderLoader);
        });
    }

    @Test
    void setTacos() {
        assertDoesNotThrow(() -> {

            List<CassandraTacoProxy> tacos = mock(List.class);
            List<TacoId> tacoIds = mock(List.class);
            Stream<CassandraTacoProxy> stream = mock(Stream.class, RETURNS_SELF);

            when(tacos.stream()).thenReturn(stream);
            when(stream.collect(any(Collector.class))).thenReturn(tacoIds);

            CassandraClientOrderProxy actual = new CassandraClientOrderProxy(clientOrder, clientOrderLoader);

            actual.setTacos(tacos);

            assertThat(actual.getTacos()).isSameAs(tacos);

            verify(clientOrder).setTacoIds(tacoIds);
            verifyNoInteractions(clientOrderLoader);
        });
    }

    @Test
    void setTacos_whenGivenListIsNull_thenReplaceWithNewEmptyCollection() {
        assertDoesNotThrow(() -> {

            CassandraClientOrderProxy actual = new CassandraClientOrderProxy(clientOrder, clientOrderLoader);

            actual.setTacos(null);

            assertThat(actual.getTacos()).isEmpty();

            verify(clientOrder).setTacoIds(any(List.class));
            verifyNoInteractions(clientOrderLoader);
        });
    }

    @Test
    void addTaco() {
        assertDoesNotThrow(() -> {

            CassandraTacoProxy taco = mock(CassandraTacoProxy.class);
            UUID tacoId = mock(UUID.class);
            when(taco.getId()).thenReturn(tacoId);

            List<CassandraTacoProxy> tacos = mock(List.class);
            List<TacoId> tacoIds = mock(List.class);
            Stream<CassandraTacoProxy> stream = mock(Stream.class, RETURNS_SELF);
            when(tacos.stream()).thenReturn(stream);
            when(stream.collect(any(Collector.class))).thenReturn(tacoIds);

            CassandraClientOrderProxy actual = new CassandraClientOrderProxy(clientOrder, clientOrderLoader)
                .setTacos(tacos);

            actual.addTaco(taco);

            verify(tacos).add(taco);
            verify(clientOrder).addTacoId(any(TacoId.class));
        });
    }

    @Test
    void addTaco_givenTacoIsNull_thenDoNothing() {
        assertDoesNotThrow(() -> {

            List<CassandraTacoProxy> tacos = mock(List.class);
            List<TacoId> tacoIds = mock(List.class);
            Stream<CassandraTacoProxy> stream = mock(Stream.class, RETURNS_SELF);
            when(tacos.stream()).thenReturn(stream);
            when(stream.collect(any(Collector.class))).thenReturn(tacoIds);

            CassandraClientOrderProxy actual = new CassandraClientOrderProxy(clientOrder, clientOrderLoader)
                .setTacos(tacos);

            actual.addTaco(null);

            verify(tacos, never()).add(any(CassandraTacoProxy.class));
            verify(clientOrder, never()).addTacoId(any(TacoId.class));
        });
    }

    @Test
    void addTaco_whenTacosIsNull_thenGenerateNewOneAndAddNewItem() {
        assertDoesNotThrow(() -> {

            CassandraTacoProxy taco = mock(CassandraTacoProxy.class);
            UUID tacoId = mock(UUID.class);
            when(taco.getId()).thenReturn(tacoId);

            CassandraClientOrderProxy actual = new CassandraClientOrderProxy(clientOrder, clientOrderLoader);

            actual.addTaco(taco);

            assertThat(actual.getTacos()).containsExactly(taco);

            verify(clientOrder).addTacoId(any(TacoId.class));
        });
    }
}
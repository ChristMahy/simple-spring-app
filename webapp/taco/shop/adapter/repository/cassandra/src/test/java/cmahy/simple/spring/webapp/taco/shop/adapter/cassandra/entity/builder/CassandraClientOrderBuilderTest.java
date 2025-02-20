package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.builder;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraClientOrder;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.loader.ClientOrderLoader;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.CassandraClientOrderProxy;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.CassandraTacoProxy;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.CassandraClientOrderProxyFactory;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.ClientOrder;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraUserProxy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CassandraClientOrderBuilderTest {

    @Mock
    private CassandraClientOrderProxyFactory clientOrderProxyFactory;

    @Mock
    private ClientOrderLoader clientOrderLoader;

    @Test
    void build() {
        assertDoesNotThrow(() -> {
            CassandraUserProxy user = mock(CassandraUserProxy.class);

            Date placedAt = new Date();

            String deliveryName = Generator.generateAString();
            String deliveryStreet = Generator.generateAString();
            String deliveryCity = Generator.generateAString();
            String deliveryState = Generator.generateAString();
            String deliveryZip = Generator.generateAString();

            String ccNumber = Generator.generateAString();
            String ccExpiration = Generator.generateAString();
            String ccCVV = Generator.generateAString();

            List<CassandraTacoProxy> tacos = Stream
                .generate(() -> mock(CassandraTacoProxy.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            when(clientOrderProxyFactory.create(any(CassandraClientOrder.class)))
                .thenAnswer(invocationOnMock -> {
                    Object methodArgument = invocationOnMock.getArgument(0);

                    assertThat(methodArgument).isInstanceOf(CassandraClientOrder.class);

                    return new CassandraClientOrderProxy((CassandraClientOrder) methodArgument, clientOrderLoader);
                });

            ClientOrder actual = new CassandraClientOrderBuilder(clientOrderProxyFactory)
                .user(user)
                .deliveryName(deliveryName)
                .deliveryStreet(deliveryStreet)
                .deliveryCity(deliveryCity)
                .deliveryState(deliveryState)
                .deliveryZip(deliveryZip)
                .ccNumber(ccNumber)
                .ccExpiration(ccExpiration)
                .ccCVV(ccCVV)
                .tacos(tacos)
                .build();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(CassandraClientOrderProxy.class);

            assertThat(actual.getId()).isNotNull();

            assertThat(actual.getUser()).isSameAs(user);

            assertThat(actual.getPlacedAt()).isAfterOrEqualTo(placedAt);

            assertThat(actual.getDeliveryName()).isEqualTo(deliveryName);
            assertThat(actual.getDeliveryStreet()).isEqualTo(deliveryStreet);
            assertThat(actual.getDeliveryCity()).isEqualTo(deliveryCity);
            assertThat(actual.getDeliveryState()).isEqualTo(deliveryState);
            assertThat(actual.getDeliveryZip()).isEqualTo(deliveryZip);

            assertThat(actual.getCcNumber()).isEqualTo(ccNumber);
            assertThat(actual.getCcExpiration()).isEqualTo(ccExpiration);
            assertThat(actual.getCcCVV()).isEqualTo(ccCVV);

            assertThat(actual.getTacos()).containsExactlyElementsOf(tacos);
        });
    }

    @Test
    void buildWithOriginal_thenReturnOriginalWithModifiedValuesAndKeepingSameId() {
        assertDoesNotThrow(() -> {
            CassandraUserProxy user = mock(CassandraUserProxy.class);

            Date placedAt = new Date();

            String deliveryName = Generator.generateAString();
            String deliveryStreet = Generator.generateAString();
            String deliveryCity = Generator.generateAString();
            String deliveryState = Generator.generateAString();
            String deliveryZip = Generator.generateAString();

            String ccNumber = Generator.generateAString();
            String ccExpiration = Generator.generateAString();
            String ccCVV = Generator.generateAString();

            List<CassandraTacoProxy> tacos = Stream
                .generate(() -> mock(CassandraTacoProxy.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            CassandraClientOrderProxy original = new CassandraClientOrderProxy(
                new CassandraClientOrder()
                    .setId(Generator.randomUUID())
                    .setPlacedAt(placedAt),
                clientOrderLoader
            )
                .setUser(mock(CassandraUserProxy.class))
                .setDeliveryName(Generator.generateAString(30))
                .setDeliveryStreet(Generator.generateAString(30))
                .setDeliveryCity(Generator.generateAString(30))
                .setDeliveryState(Generator.generateAString(30))
                .setDeliveryZip(Generator.generateAString(30))
                .setCcNumber(Generator.generateAString(30))
                .setCcExpiration(Generator.generateAString(30))
                .setCcCVV(Generator.generateAString(30))
                .setTacos(
                    Stream
                        .generate(() -> mock(CassandraTacoProxy.class))
                        .limit(30)
                        .toList()
                );

            ClientOrder actual = new CassandraClientOrderBuilder(clientOrderProxyFactory, original)
                .user(user)
                .deliveryName(deliveryName)
                .deliveryStreet(deliveryStreet)
                .deliveryCity(deliveryCity)
                .deliveryState(deliveryState)
                .deliveryZip(deliveryZip)
                .ccNumber(ccNumber)
                .ccExpiration(ccExpiration)
                .ccCVV(ccCVV)
                .tacos(tacos)
                .build();

            assertThat(actual)
                .isNotNull()
                .isSameAs(original);

            assertThat(actual.getId()).isEqualTo(original.getId());

            assertThat(actual.getUser()).isSameAs(user);

            assertThat(actual.getPlacedAt()).isEqualTo(placedAt);

            assertThat(actual.getDeliveryName()).isEqualTo(deliveryName);
            assertThat(actual.getDeliveryStreet()).isEqualTo(deliveryStreet);
            assertThat(actual.getDeliveryCity()).isEqualTo(deliveryCity);
            assertThat(actual.getDeliveryState()).isEqualTo(deliveryState);
            assertThat(actual.getDeliveryZip()).isEqualTo(deliveryZip);

            assertThat(actual.getCcNumber()).isEqualTo(ccNumber);
            assertThat(actual.getCcExpiration()).isEqualTo(ccExpiration);
            assertThat(actual.getCcCVV()).isEqualTo(ccCVV);

            assertThat(actual.getTacos()).containsExactlyElementsOf(tacos);
        });
    }

    @Test
    void buildWithNullAsOriginal_thenBuildNewOne() {
        assertDoesNotThrow(() -> {
            CassandraUserProxy user = mock(CassandraUserProxy.class);

            Date placedAt = new Date();

            String deliveryName = Generator.generateAString();
            String deliveryStreet = Generator.generateAString();
            String deliveryCity = Generator.generateAString();
            String deliveryState = Generator.generateAString();
            String deliveryZip = Generator.generateAString();

            String ccNumber = Generator.generateAString();
            String ccExpiration = Generator.generateAString();
            String ccCVV = Generator.generateAString();

            List<CassandraTacoProxy> tacos = Stream
                .generate(() -> mock(CassandraTacoProxy.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            when(clientOrderProxyFactory.create(any(CassandraClientOrder.class)))
                .thenAnswer(invocationOnMock -> {
                    Object methodArgument = invocationOnMock.getArgument(0);

                    assertThat(methodArgument).isInstanceOf(CassandraClientOrder.class);

                    return new CassandraClientOrderProxy((CassandraClientOrder) methodArgument, clientOrderLoader);
                });

            ClientOrder actual = new CassandraClientOrderBuilder(clientOrderProxyFactory, null)
                .user(user)
                .deliveryName(deliveryName)
                .deliveryStreet(deliveryStreet)
                .deliveryCity(deliveryCity)
                .deliveryState(deliveryState)
                .deliveryZip(deliveryZip)
                .ccNumber(ccNumber)
                .ccExpiration(ccExpiration)
                .ccCVV(ccCVV)
                .tacos(tacos)
                .build();

            assertThat(actual).isNotNull();

            assertThat(actual.getId()).isNotNull();

            assertThat(actual.getUser()).isSameAs(user);

            assertThat(actual.getPlacedAt()).isAfterOrEqualTo(placedAt);

            assertThat(actual.getDeliveryName()).isEqualTo(deliveryName);
            assertThat(actual.getDeliveryStreet()).isEqualTo(deliveryStreet);
            assertThat(actual.getDeliveryCity()).isEqualTo(deliveryCity);
            assertThat(actual.getDeliveryState()).isEqualTo(deliveryState);
            assertThat(actual.getDeliveryZip()).isEqualTo(deliveryZip);

            assertThat(actual.getCcNumber()).isEqualTo(ccNumber);
            assertThat(actual.getCcExpiration()).isEqualTo(ccExpiration);
            assertThat(actual.getCcCVV()).isEqualTo(ccCVV);

            assertThat(actual.getTacos()).containsExactlyElementsOf(tacos);
        });
    }
}
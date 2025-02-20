package cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.test;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.*;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.ClientOrderBuilderStub;
import cmahy.simple.spring.webapp.user.kernel.domain.UserStub;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

class ClientOrderBuilderStubTest {

    @Test
    void build() {
        assertDoesNotThrow(() -> {
            UserStub user = mock(UserStub.class);

            String deliveryName = Generator.generateAString();
            String deliveryStreet = Generator.generateAString();
            String deliveryCity = Generator.generateAString();
            String deliveryState = Generator.generateAString();
            String deliveryZip = Generator.generateAString();

            String ccNumber = Generator.generateAString();
            String ccExpiration = Generator.generateAString();
            String ccCVV = Generator.generateAString();

            List<TacoStub> tacos = Stream
                .generate(() -> mock(TacoStub.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            ClientOrder actual = new ClientOrderBuilderStub()
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
                .isInstanceOf(ClientOrderStub.class);

            assertThat(actual.getId()).isNull();
            assertThat(actual.getPlacedAt()).isNull();

            assertThat(actual.getUser()).isSameAs(user);

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
            UserStub user = mock(UserStub.class);

            Date placedAt = new Date();

            String deliveryName = Generator.generateAString();
            String deliveryStreet = Generator.generateAString();
            String deliveryCity = Generator.generateAString();
            String deliveryState = Generator.generateAString();
            String deliveryZip = Generator.generateAString();

            String ccNumber = Generator.generateAString();
            String ccExpiration = Generator.generateAString();
            String ccCVV = Generator.generateAString();

            List<TacoStub> tacos = Stream
                .generate(() -> mock(TacoStub.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            ClientOrderStub original = new ClientOrderStub()
                .setId(Generator.randomUUID())
                .setUser(mock(UserStub.class))
                .setPlacedAt(placedAt)
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
                        .generate(() -> mock(TacoStub.class))
                        .limit(30)
                        .toList()
                );

            ClientOrder actual = new ClientOrderBuilderStub(original)
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
            UserStub user = mock(UserStub.class);

            String deliveryName = Generator.generateAString();
            String deliveryStreet = Generator.generateAString();
            String deliveryCity = Generator.generateAString();
            String deliveryState = Generator.generateAString();
            String deliveryZip = Generator.generateAString();

            String ccNumber = Generator.generateAString();
            String ccExpiration = Generator.generateAString();
            String ccCVV = Generator.generateAString();

            List<TacoStub> tacos = Stream
                .generate(() -> mock(TacoStub.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            ClientOrder actual = new ClientOrderBuilderStub(null)
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

            assertThat(actual.getId()).isNull();
            assertThat(actual.getPlacedAt()).isNull();

            assertThat(actual.getUser()).isSameAs(user);

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
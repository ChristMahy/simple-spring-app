package cmahy.simple.spring.webapp.taco.shop.adapter.webclient.entity.builder;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.taco.shop.adapter.webclient.entity.domain.ExternalClientOrder;
import cmahy.simple.spring.webapp.taco.shop.adapter.webclient.entity.domain.ExternalTaco;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.ClientOrder;
import cmahy.simple.spring.webapp.user.adapter.webclient.entity.domain.ExternalUser;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

class ExternalClientOrderBuilderTest {
    
    @Test
    void build() {
        assertDoesNotThrow(() -> {
            ExternalUser user = mock(ExternalUser.class);

            Date placedAt = new Date();

            String deliveryName = Generator.generateAString();
            String deliveryStreet = Generator.generateAString();
            String deliveryCity = Generator.generateAString();
            String deliveryState = Generator.generateAString();
            String deliveryZip = Generator.generateAString();

            String ccNumber = Generator.generateAString();
            String ccExpiration = Generator.generateAString();
            String ccCVV = Generator.generateAString();

            List<ExternalTaco> tacos = Stream
                .generate(() -> mock(ExternalTaco.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            ClientOrder actual = new ExternalClientOrderBuilder()
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
                .isInstanceOf(ExternalClientOrder.class);

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
            ExternalUser user = mock(ExternalUser.class);

            Date placedAt = new Date();

            String deliveryName = Generator.generateAString();
            String deliveryStreet = Generator.generateAString();
            String deliveryCity = Generator.generateAString();
            String deliveryState = Generator.generateAString();
            String deliveryZip = Generator.generateAString();

            String ccNumber = Generator.generateAString();
            String ccExpiration = Generator.generateAString();
            String ccCVV = Generator.generateAString();

            List<ExternalTaco> tacos = Stream
                .generate(() -> mock(ExternalTaco.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            ExternalClientOrder original = new ExternalClientOrder()
                .setId(Generator.randomUUID())
                .setUser(mock(ExternalUser.class))
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
                        .generate(() -> mock(ExternalTaco.class))
                        .limit(30)
                        .toList()
                );

            ClientOrder actual = new ExternalClientOrderBuilder(original)
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
    void buildWithOriginal_thenReturnOriginalWithoutModifiedValuesAndKeepingSameValues() {
        assertDoesNotThrow(() -> {
            Date placedAt = new Date();

            ExternalClientOrder original = new ExternalClientOrder()
                .setId(Generator.randomUUID())
                .setUser(mock(ExternalUser.class))
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
                        .generate(() -> mock(ExternalTaco.class))
                        .limit(30)
                        .toList()
                );

            ClientOrder actual = new ExternalClientOrderBuilder(original).build();

            assertThat(actual)
                .isNotNull()
                .isSameAs(original);

            assertThat(actual.getId()).isEqualTo(original.getId());

            assertThat(actual.getUser()).isSameAs(original.getUser());

            assertThat(actual.getPlacedAt()).isEqualTo(placedAt);

            assertThat(actual.getDeliveryName()).isEqualTo(original.getDeliveryName());
            assertThat(actual.getDeliveryStreet()).isEqualTo(original.getDeliveryStreet());
            assertThat(actual.getDeliveryCity()).isEqualTo(original.getDeliveryCity());
            assertThat(actual.getDeliveryState()).isEqualTo(original.getDeliveryState());
            assertThat(actual.getDeliveryZip()).isEqualTo(original.getDeliveryZip());

            assertThat(actual.getCcNumber()).isEqualTo(original.getCcNumber());
            assertThat(actual.getCcExpiration()).isEqualTo(original.getCcExpiration());
            assertThat(actual.getCcCVV()).isEqualTo(original.getCcCVV());

            assertThat(actual.getTacos()).containsExactlyElementsOf(original.getTacos());
        });
    }

    @Test
    void buildWithNullAsOriginal_thenBuildNewOne() {
        assertDoesNotThrow(() -> {
            ExternalUser user = mock(ExternalUser.class);

            String deliveryName = Generator.generateAString();
            String deliveryStreet = Generator.generateAString();
            String deliveryCity = Generator.generateAString();
            String deliveryState = Generator.generateAString();
            String deliveryZip = Generator.generateAString();

            String ccNumber = Generator.generateAString();
            String ccExpiration = Generator.generateAString();
            String ccCVV = Generator.generateAString();

            List<ExternalTaco> tacos = Stream
                .generate(() -> mock(ExternalTaco.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            ClientOrder actual = new ExternalClientOrderBuilder(null)
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
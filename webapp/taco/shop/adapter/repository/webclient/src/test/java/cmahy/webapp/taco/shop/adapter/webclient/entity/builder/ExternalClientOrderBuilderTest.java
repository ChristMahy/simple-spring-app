package cmahy.webapp.taco.shop.adapter.webclient.entity.builder;

import cmahy.common.helper.Generator;
import cmahy.webapp.taco.shop.adapter.webclient.entity.ExternalClientOrder;
import cmahy.webapp.taco.shop.adapter.webclient.entity.ExternalTaco;
import cmahy.webapp.taco.shop.kernel.domain.ClientOrder;
import cmahy.webapp.user.adapter.webclient.entity.ExternalUser;
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
                .placedAt(placedAt)
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
                .setId(Generator.randomLongEqualOrAboveZero())
                .setUser(mock(ExternalUser.class))
                .setPlacedAt(new Date())
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
                .placedAt(placedAt)
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

            ClientOrder actual = new ExternalClientOrderBuilder(null)
                .user(user)
                .placedAt(placedAt)
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
}
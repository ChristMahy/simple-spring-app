package cmahy.simple.spring.webapp.taco.shop.kernel.domain.test;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.*;
import cmahy.simple.spring.webapp.user.kernel.domain.UserStub;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

class ClientOrderStubTest {

    @Test
    void clientOrderNewInstance_whenAllFieldsComplete_thenFieldsReturnValue() {
        assertDoesNotThrow(() -> {
            UUID id = Generator.randomUUID();

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

            ClientOrder actual = new ClientOrderStub()
                .setId(id)
                .setUser(user)
                .setPlacedAt(placedAt)
                .setDeliveryName(deliveryName)
                .setDeliveryStreet(deliveryStreet)
                .setDeliveryCity(deliveryCity)
                .setDeliveryState(deliveryState)
                .setDeliveryZip(deliveryZip)
                .setCcNumber(ccNumber)
                .setCcExpiration(ccExpiration)
                .setCcCVV(ccCVV)
                .setTacos(tacos);

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(ClientOrderStub.class);

            assertThat(actual.getId()).isEqualTo(id);

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
    void clientOrderNewInstance_whenAllFieldsEmpty_thenFieldsReturnNull() {
        assertDoesNotThrow(() -> {
            ClientOrder actual = new ClientOrderStub();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(ClientOrderStub.class);

            assertThat(actual.getId()).isNull();

            assertThat(actual.getUser()).isNull();

            assertThat(actual.getPlacedAt()).isNull();

            assertThat(actual.getDeliveryName()).isNull();
            assertThat(actual.getDeliveryStreet()).isNull();
            assertThat(actual.getDeliveryCity()).isNull();
            assertThat(actual.getDeliveryState()).isNull();
            assertThat(actual.getDeliveryZip()).isNull();

            assertThat(actual.getCcNumber()).isNull();
            assertThat(actual.getCcExpiration()).isNull();
            assertThat(actual.getCcCVV()).isNull();

            assertThat(actual.getTacos()).isNull();
        });
    }

    @Test
    void clientOrderNewInstance_whenUseSetterToOverrideValueToNull_thenFieldsReturnNull() {
        assertDoesNotThrow(() -> {
            UUID id = Generator.randomUUID();

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

            ClientOrderStub actual = new ClientOrderStub()
                .setId(id)
                .setUser(user)
                .setPlacedAt(placedAt)
                .setDeliveryName(deliveryName)
                .setDeliveryStreet(deliveryStreet)
                .setDeliveryCity(deliveryCity)
                .setDeliveryState(deliveryState)
                .setDeliveryZip(deliveryZip)
                .setCcNumber(ccNumber)
                .setCcExpiration(ccExpiration)
                .setCcCVV(ccCVV)
                .setTacos(tacos);

            actual = actual
                .setId(null)
                .setUser(null)
                .setPlacedAt(null)
                .setDeliveryName(null)
                .setDeliveryStreet(null)
                .setDeliveryCity(null)
                .setDeliveryState(null)
                .setDeliveryZip(null)
                .setCcNumber(null)
                .setCcExpiration(null)
                .setCcCVV(null)
                .setTacos(null);

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(ClientOrderStub.class);

            assertThat(actual.getId()).isNull();

            assertThat(actual.getUser()).isNull();

            assertThat(actual.getPlacedAt()).isNull();

            assertThat(actual.getDeliveryName()).isNull();
            assertThat(actual.getDeliveryStreet()).isNull();
            assertThat(actual.getDeliveryCity()).isNull();
            assertThat(actual.getDeliveryState()).isNull();
            assertThat(actual.getDeliveryZip()).isNull();

            assertThat(actual.getCcNumber()).isNull();
            assertThat(actual.getCcExpiration()).isNull();
            assertThat(actual.getCcCVV()).isNull();

            assertThat(actual.getTacos()).isNull();
        });
    }

    @Test
    void clientOrder_whenAddTaco_thenShouldBePresentInIngredientsList() {
        assertDoesNotThrow(() -> {
            UUID id = Generator.randomUUID();

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

            ClientOrder actual = new ClientOrderStub()
                .setId(id)
                .setUser(user)
                .setPlacedAt(placedAt)
                .setDeliveryName(deliveryName)
                .setDeliveryStreet(deliveryStreet)
                .setDeliveryCity(deliveryCity)
                .setDeliveryState(deliveryState)
                .setDeliveryZip(deliveryZip)
                .setCcNumber(ccNumber)
                .setCcExpiration(ccExpiration)
                .setCcCVV(ccCVV)
                .setTacos(tacos);

            TacoStub newOne = mock(TacoStub.class);
            actual.addTaco(newOne);

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(ClientOrderStub.class);

            List<TacoStub> tacosExpected = new ArrayList<>(tacos.size() + 1) {{
                addAll(tacos);
                add(newOne);
            }};

            assertThat(actual.getId()).isEqualTo(id);

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

            assertThat(actual.getTacos()).containsExactlyElementsOf(tacosExpected);
        });
    }

    @Test
    void clientOrder_whenAddNullTaco_thenShouldNotBePresentInIngredientsList() {
        assertDoesNotThrow(() -> {
            UUID id = Generator.randomUUID();

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

            ClientOrder actual = new ClientOrderStub()
                .setId(id)
                .setUser(user)
                .setPlacedAt(placedAt)
                .setDeliveryName(deliveryName)
                .setDeliveryStreet(deliveryStreet)
                .setDeliveryCity(deliveryCity)
                .setDeliveryState(deliveryState)
                .setDeliveryZip(deliveryZip)
                .setCcNumber(ccNumber)
                .setCcExpiration(ccExpiration)
                .setCcCVV(ccCVV)
                .setTacos(tacos);

            actual.addTaco(null);

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(ClientOrderStub.class);

            assertThat(actual.getId()).isEqualTo(id);

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
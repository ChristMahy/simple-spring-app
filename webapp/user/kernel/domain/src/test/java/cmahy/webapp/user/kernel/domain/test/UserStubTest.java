package cmahy.webapp.user.kernel.domain.test;

import cmahy.common.helper.Generator;
import cmahy.webapp.user.kernel.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class UserStubTest {

    @Test
    void userNewInstance_whenAllFieldsComplete_thenFieldsReturnValue() {
        assertDoesNotThrow(() -> {
            UUID id = Generator.randomUUID();
            String userName = Generator.generateAString();
            byte[] password = Generator.randomBytes(300);
            String fullName = Generator.generateAString();
            String street = Generator.generateAString();
            String city = Generator.generateAString();
            String state = Generator.generateAString();
            String zip = Generator.generateAString();
            String phoneNumber = Generator.generateAString();
            Collection<RoleStub> roles = Stream
                .generate(() -> mock(RoleStub.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            User actual = new UserStub()
                .setId(id)
                .setUserName(userName)
                .setPassword(password)
                .setFullName(fullName)
                .setStreet(street)
                .setCity(city)
                .setState(state)
                .setZip(zip)
                .setPhoneNumber(phoneNumber)
                .setRoles(roles);

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(UserStub.class);

            assertThat(actual.getId()).isEqualTo(id);
            assertThat(actual.getUserName()).isEqualTo(userName);
            assertThat(actual.getPassword()).isEqualTo(password);
            assertThat(actual.getFullName()).isEqualTo(fullName);
            assertThat(actual.getStreet()).isEqualTo(street);
            assertThat(actual.getCity()).isEqualTo(city);
            assertThat(actual.getState()).isEqualTo(state);
            assertThat(actual.getZip()).isEqualTo(zip);
            assertThat(actual.getPhoneNumber()).isEqualTo(phoneNumber);
            assertThat(actual.getRoles()).containsExactlyElementsOf(roles);
        });
    }

    @Test
    void userNewInstance_whenAllFieldsEmpty_thenFieldsReturnNull() {
        assertDoesNotThrow(() -> {
            UUID id = Generator.randomUUID();
            String userName = Generator.generateAString();
            byte[] password = Generator.randomBytes(300);
            String fullName = Generator.generateAString();
            String street = Generator.generateAString();
            String city = Generator.generateAString();
            String state = Generator.generateAString();
            String zip = Generator.generateAString();
            String phoneNumber = Generator.generateAString();
            Collection<RoleStub> roles = Stream
                .generate(() -> mock(RoleStub.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            UserStub actual = new UserStub()
                .setId(id)
                .setUserName(userName)
                .setPassword(password)
                .setFullName(fullName)
                .setStreet(street)
                .setCity(city)
                .setState(state)
                .setZip(zip)
                .setPhoneNumber(phoneNumber)
                .setRoles(roles);

            actual = actual
                .setId(null)
                .setUserName(null)
                .setPassword(null)
                .setFullName(null)
                .setStreet(null)
                .setCity(null)
                .setState(null)
                .setZip(null)
                .setPhoneNumber(null)
                .setRoles(null);

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(UserStub.class);

            assertThat(actual.getId()).isNull();
            assertThat(actual.getUserName()).isNull();
            assertThat(actual.getPassword()).isNull();
            assertThat(actual.getFullName()).isNull();
            assertThat(actual.getStreet()).isNull();
            assertThat(actual.getCity()).isNull();
            assertThat(actual.getState()).isNull();
            assertThat(actual.getZip()).isNull();
            assertThat(actual.getPhoneNumber()).isNull();
            assertThat(actual.getRoles()).isNull();
        });
    }

    @Test
    void userNewInstance_whenUseSetterToOverrideValueToNull_thenFieldsReturnNull() {
        assertDoesNotThrow(() -> {
            User actual = new UserStub();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(UserStub.class);

            assertThat(actual.getId()).isNull();
            assertThat(actual.getUserName()).isNull();
            assertThat(actual.getPassword()).isNull();
            assertThat(actual.getFullName()).isNull();
            assertThat(actual.getStreet()).isNull();
            assertThat(actual.getCity()).isNull();
            assertThat(actual.getState()).isNull();
            assertThat(actual.getZip()).isNull();
            assertThat(actual.getPhoneNumber()).isNull();
            assertThat(actual.getRoles()).isNull();
        });
    }
}
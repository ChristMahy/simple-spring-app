package cmahy.webapp.user.kernel.domain.test;

import cmahy.common.helper.Generator;
import cmahy.webapp.user.kernel.domain.*;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

class UserSecurityStubTest {

    @Test
    void userSecurityNewInstance_whenAllFieldsComplete_thenFieldsReturnValue() {
        assertDoesNotThrow(() -> {
            Long id = Generator.randomLongEqualOrAboveZero();
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
            AuthProvider authProvider = Generator.randomEnum(AuthProvider.class);
            Boolean expired = Generator.randomBoolean();
            Boolean locked = Generator.randomBoolean();
            Boolean enabled = Generator.randomBoolean();
            Boolean credentialsExpired = Generator.randomBoolean();

            UserSecurity actual = new UserSecurityStub()
                .setId(id)
                .setUserName(userName)
                .setPassword(password)
                .setFullName(fullName)
                .setStreet(street)
                .setCity(city)
                .setState(state)
                .setZip(zip)
                .setPhoneNumber(phoneNumber)
                .setRoles(roles)
                .setAuthProvider(authProvider)
                .setExpired(expired)
                .setLocked(locked)
                .setEnabled(enabled)
                .setCredentialsExpired(credentialsExpired);

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(UserSecurityStub.class);

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
            assertThat(actual.getAuthProvider()).isEqualTo(authProvider);
            assertThat(actual.getExpired()).isEqualTo(expired);
            assertThat(actual.getLocked()).isEqualTo(locked);
            assertThat(actual.getEnabled()).isEqualTo(enabled);
            assertThat(actual.getCredentialsExpired()).isEqualTo(credentialsExpired);
        });
    }

    @Test
    void userSecurityNewInstance_whenAllFieldsEmpty_thenFieldsReturnNull() {
        assertDoesNotThrow(() -> {
            UserSecurity actual = new UserSecurityStub();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(UserSecurityStub.class);

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
            assertThat(actual.getAuthProvider()).isNull();
            assertThat(actual.getExpired()).isNull();
            assertThat(actual.getLocked()).isNull();
            assertThat(actual.getEnabled()).isNull();
            assertThat(actual.getCredentialsExpired()).isNull();
        });
    }

    @Test
    void userSecurityNewInstance_whenUseSetterToOverrideValueToNull_thenFieldsReturnNull() {
        assertDoesNotThrow(() -> {
            Long id = Generator.randomLongEqualOrAboveZero();
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
            AuthProvider authProvider = Generator.randomEnum(AuthProvider.class);
            Boolean expired = Generator.randomBoolean();
            Boolean locked = Generator.randomBoolean();
            Boolean enabled = Generator.randomBoolean();
            Boolean credentialsExpired = Generator.randomBoolean();

            UserSecurityStub actual = new UserSecurityStub()
                .setId(id)
                .setUserName(userName)
                .setPassword(password)
                .setFullName(fullName)
                .setStreet(street)
                .setCity(city)
                .setState(state)
                .setZip(zip)
                .setPhoneNumber(phoneNumber)
                .setRoles(roles)
                .setAuthProvider(authProvider)
                .setExpired(expired)
                .setLocked(locked)
                .setEnabled(enabled)
                .setCredentialsExpired(credentialsExpired);

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
                .setRoles(null)
                .setAuthProvider(null)
                .setExpired(null)
                .setLocked(null)
                .setEnabled(null)
                .setCredentialsExpired(null);

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(UserSecurityStub.class);

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
            assertThat(actual.getAuthProvider()).isNull();
            assertThat(actual.getExpired()).isNull();
            assertThat(actual.getLocked()).isNull();
            assertThat(actual.getEnabled()).isNull();
            assertThat(actual.getCredentialsExpired()).isNull();
        });
    }
}
package cmahy.simple.spring.webapp.user.adapter.jpa.entity.builder;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.user.adapter.jpa.entity.domain.JpaRole;
import cmahy.simple.spring.webapp.user.adapter.jpa.entity.domain.JpaUserSecurity;
import cmahy.simple.spring.webapp.user.kernel.domain.AuthProvider;
import cmahy.simple.spring.webapp.user.kernel.domain.UserSecurity;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

class JpaUserSecurityBuilderTest {

    @Test
    void build() {
        assertDoesNotThrow(() -> {
            String userName = Generator.generateAString();
            byte[] password = Generator.randomBytes(Generator.randomInt(50, 300));
            String fullName = Generator.generateAString();

            String street = Generator.generateAString();
            String city = Generator.generateAString();
            String state = Generator.generateAString();
            String zip = Generator.generateAString();
            String phoneNumber = Generator.generateAString();

            Collection<JpaRole> roles = Stream
                .generate(() -> mock(JpaRole.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            AuthProvider authProvider = Generator.randomEnum(AuthProvider.class);

            Boolean isExpired = Generator.randomBoolean();
            Boolean isLocked = Generator.randomBoolean();
            Boolean isEnabled = Generator.randomBoolean();
            Boolean isCredentialsExpired = Generator.randomBoolean();

            UserSecurity actual = new JpaUserSecurityBuilder()
                .userName(userName)
                .password(password)
                .fullName(fullName)
                .street(street)
                .city(city)
                .state(state)
                .zip(zip)
                .phoneNumber(phoneNumber)
                .roles(roles)
                .authProvider(authProvider)
                .expired(isExpired)
                .locked(isLocked)
                .enabled(isEnabled)
                .credentialsExpired(isCredentialsExpired)
                .build();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(JpaUserSecurity.class);

            assertThat(actual.getId()).isNull();

            assertThat(actual.getUserName()).isEqualTo(userName);
            assertThat(actual.getPassword()).isEqualTo(password);
            assertThat(actual.getFullName()).isEqualTo(fullName);

            assertThat(actual.getStreet()).isEqualTo(street);
            assertThat(actual.getCity()).isEqualTo(city);
            assertThat(actual.getState()).isEqualTo(state);
            assertThat(actual.getZip()).isEqualTo(zip);
            assertThat(actual.getPhoneNumber()).isEqualTo(phoneNumber);

            assertThat(actual.getRoles()).isEqualTo(roles);

            assertThat(actual.getAuthProvider()).isEqualTo(authProvider);
            assertThat(actual.getExpired()).isEqualTo(isExpired);
            assertThat(actual.getLocked()).isEqualTo(isLocked);
            assertThat(actual.getEnabled()).isEqualTo(isEnabled);
            assertThat(actual.getCredentialsExpired()).isEqualTo(isCredentialsExpired);
        });
    }

    @Test
    void buildWithOriginal_thenReturnOriginalWithModifiedValuesAndKeepingSameId() {
        assertDoesNotThrow(() -> {
            String newUserName = Generator.generateAString(300);
            byte[] newPassword = Generator.randomBytes(Generator.randomInt(200, 300));
            String newFullName = Generator.generateAString(300);

            String newStreet = Generator.generateAString(300);
            String newCity = Generator.generateAString(300);
            String newState = Generator.generateAString(300);
            String newZip = Generator.generateAString(300);
            String newPhoneNumber = Generator.generateAString(300);

            Collection<JpaRole> newRoles = Stream
                .generate(() -> mock(JpaRole.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            AuthProvider newAuthProvider = Generator.randomEnum(AuthProvider.class);

            Boolean newIsExpired = Generator.randomBoolean();
            Boolean newIsLocked = Generator.randomBoolean();
            Boolean newIsEnabled = Generator.randomBoolean();
            Boolean newIsCredentialsExpired = Generator.randomBoolean();

            JpaUserSecurity original = new JpaUserSecurity()
                .setId(Generator.randomUUID())
                .setUserName(Generator.generateAString(30))
                .setPassword(Generator.randomBytes(Generator.randomInt(20, 30)))
                .setFullName(Generator.generateAString(30))
                .setStreet(Generator.generateAString(30))
                .setCity(Generator.generateAString(30))
                .setState(Generator.generateAString(30))
                .setZip(Generator.generateAString(30))
                .setPhoneNumber(Generator.generateAString(30))
                .setRoles(
                    Stream
                        .generate(() -> mock(JpaRole.class))
                        .limit(Generator.randomInt(10, 20))
                        .toList()
                )
                .setAuthProvider(Generator.randomEnum(AuthProvider.class))
                .setExpired(Generator.randomBoolean())
                .setLocked(Generator.randomBoolean())
                .setEnabled(Generator.randomBoolean())
                .setCredentialsExpired(Generator.randomBoolean());

            UserSecurity actual = new JpaUserSecurityBuilder(original)
                .userName(newUserName)
                .password(newPassword)
                .fullName(newFullName)
                .street(newStreet)
                .city(newCity)
                .state(newState)
                .zip(newZip)
                .phoneNumber(newPhoneNumber)
                .roles(newRoles)
                .authProvider(newAuthProvider)
                .expired(newIsExpired)
                .locked(newIsLocked)
                .enabled(newIsEnabled)
                .credentialsExpired(newIsCredentialsExpired)
                .build();

            assertThat(actual)
                .isNotNull()
                .isSameAs(original);

            assertThat(actual.getId()).isEqualTo(original.getId());

            assertThat(actual.getUserName()).isEqualTo(newUserName);
            assertThat(actual.getPassword()).isEqualTo(newPassword);
            assertThat(actual.getFullName()).isEqualTo(newFullName);

            assertThat(actual.getStreet()).isEqualTo(newStreet);
            assertThat(actual.getCity()).isEqualTo(newCity);
            assertThat(actual.getState()).isEqualTo(newState);
            assertThat(actual.getZip()).isEqualTo(newZip);
            assertThat(actual.getPhoneNumber()).isEqualTo(newPhoneNumber);

            assertThat(actual.getRoles()).isEqualTo(newRoles);

            assertThat(actual.getAuthProvider()).isEqualTo(newAuthProvider);
            assertThat(actual.getExpired()).isEqualTo(newIsExpired);
            assertThat(actual.getLocked()).isEqualTo(newIsLocked);
            assertThat(actual.getEnabled()).isEqualTo(newIsEnabled);
            assertThat(actual.getCredentialsExpired()).isEqualTo(newIsCredentialsExpired);
        });
    }

    @Test
    void buildWithOriginal_thenReturnOriginalWithoutModifiedValuesAndKeepingAllSameValues() {
        assertDoesNotThrow(() -> {

            JpaUserSecurity original = new JpaUserSecurity()
                .setId(Generator.randomUUID())
                .setUserName(Generator.generateAString(30))
                .setPassword(Generator.randomBytes(Generator.randomInt(20, 30)))
                .setFullName(Generator.generateAString(30))
                .setStreet(Generator.generateAString(30))
                .setCity(Generator.generateAString(30))
                .setState(Generator.generateAString(30))
                .setZip(Generator.generateAString(30))
                .setPhoneNumber(Generator.generateAString(30))
                .setRoles(
                    Stream
                        .generate(() -> mock(JpaRole.class))
                        .limit(Generator.randomInt(10, 20))
                        .toList()
                )
                .setAuthProvider(Generator.randomEnum(AuthProvider.class))
                .setExpired(Generator.randomBoolean())
                .setLocked(Generator.randomBoolean())
                .setEnabled(Generator.randomBoolean())
                .setCredentialsExpired(Generator.randomBoolean());

            UserSecurity actual = new JpaUserSecurityBuilder(original).build();

            assertThat(actual)
                .isNotNull()
                .isSameAs(original);

            assertThat(actual.getId()).isEqualTo(original.getId());

            assertThat(actual.getUserName()).isEqualTo(original.getUserName());
            assertThat(actual.getPassword()).isEqualTo(original.getPassword());
            assertThat(actual.getFullName()).isEqualTo(original.getFullName());

            assertThat(actual.getStreet()).isEqualTo(original.getStreet());
            assertThat(actual.getCity()).isEqualTo(original.getCity());
            assertThat(actual.getState()).isEqualTo(original.getState());
            assertThat(actual.getZip()).isEqualTo(original.getZip());
            assertThat(actual.getPhoneNumber()).isEqualTo(original.getPhoneNumber());

            assertThat(actual.getRoles()).isEqualTo(original.getRoles());

            assertThat(actual.getAuthProvider()).isEqualTo(original.getAuthProvider());
            assertThat(actual.getExpired()).isEqualTo(original.getExpired());
            assertThat(actual.getLocked()).isEqualTo(original.getLocked());
            assertThat(actual.getEnabled()).isEqualTo(original.getEnabled());
            assertThat(actual.getCredentialsExpired()).isEqualTo(original.getCredentialsExpired());
        });
    }

    @Test
    void buildWithNullAsOriginal_thenBuildNewOne() {
        assertDoesNotThrow(() -> {
            String userName = Generator.generateAString();
            byte[] password = Generator.randomBytes(Generator.randomInt(50, 300));
            String fullName = Generator.generateAString();

            String street = Generator.generateAString();
            String city = Generator.generateAString();
            String state = Generator.generateAString();
            String zip = Generator.generateAString();
            String phoneNumber = Generator.generateAString();

            Collection<JpaRole> roles = Stream
                .generate(() -> mock(JpaRole.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            AuthProvider authProvider = Generator.randomEnum(AuthProvider.class);

            Boolean isExpired = Generator.randomBoolean();
            Boolean isLocked = Generator.randomBoolean();
            Boolean isEnabled = Generator.randomBoolean();
            Boolean isCredentialsExpired = Generator.randomBoolean();

            UserSecurity actual = new JpaUserSecurityBuilder(null)
                .userName(userName)
                .password(password)
                .fullName(fullName)
                .street(street)
                .city(city)
                .state(state)
                .zip(zip)
                .phoneNumber(phoneNumber)
                .roles(roles)
                .authProvider(authProvider)
                .expired(isExpired)
                .locked(isLocked)
                .enabled(isEnabled)
                .credentialsExpired(isCredentialsExpired)
                .build();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(JpaUserSecurity.class);

            assertThat(actual.getId()).isNull();

            assertThat(actual.getUserName()).isEqualTo(userName);
            assertThat(actual.getPassword()).isEqualTo(password);
            assertThat(actual.getFullName()).isEqualTo(fullName);

            assertThat(actual.getStreet()).isEqualTo(street);
            assertThat(actual.getCity()).isEqualTo(city);
            assertThat(actual.getState()).isEqualTo(state);
            assertThat(actual.getZip()).isEqualTo(zip);
            assertThat(actual.getPhoneNumber()).isEqualTo(phoneNumber);

            assertThat(actual.getRoles()).isEqualTo(roles);

            assertThat(actual.getAuthProvider()).isEqualTo(authProvider);
            assertThat(actual.getExpired()).isEqualTo(isExpired);
            assertThat(actual.getLocked()).isEqualTo(isLocked);
            assertThat(actual.getEnabled()).isEqualTo(isEnabled);
            assertThat(actual.getCredentialsExpired()).isEqualTo(isCredentialsExpired);
        });
    }
}
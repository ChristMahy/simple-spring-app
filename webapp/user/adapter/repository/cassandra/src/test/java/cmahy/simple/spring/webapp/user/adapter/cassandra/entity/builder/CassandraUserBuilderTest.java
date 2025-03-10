package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.builder;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraUserImpl;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader.UserLoader;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraRoleProxy;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraUserProxy;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraUserProxyFactory;
import cmahy.simple.spring.webapp.user.kernel.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CassandraUserBuilderTest {

    @Mock
    private CassandraUserProxyFactory userProxyFactory;

    @Mock
    private UserLoader userLoader;

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

            Collection<CassandraRoleProxy> roles = Stream
                .generate(() -> mock(CassandraRoleProxy.class))
                .limit(Generator.randomInt(50, 500))
                .collect(Collectors.toSet());

            when(userProxyFactory.create(any(CassandraUserImpl.class)))
                .thenAnswer(invocationOnMock -> {
                    Object methodArgument = invocationOnMock.getArgument(0);

                    assertThat(methodArgument).isInstanceOf(CassandraUserImpl.class);

                    return new CassandraUserProxy((CassandraUserImpl) methodArgument, userLoader);
                });

            User actual = new CassandraUserBuilder(userProxyFactory)
                .userName(userName)
                .password(password)
                .fullName(fullName)
                .street(street)
                .city(city)
                .state(state)
                .zip(zip)
                .phoneNumber(phoneNumber)
                .roles(roles)
                .build();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(CassandraUserProxy.class);

            assertThat(actual.getId()).isNull();

            assertThat(actual.getUserName()).isEqualTo(userName);
            assertThat(actual.getPassword()).isEqualTo(password);
            assertThat(actual.getFullName()).isEqualTo(fullName);

            assertThat(actual.getStreet()).isEqualTo(street);
            assertThat(actual.getCity()).isEqualTo(city);
            assertThat(actual.getState()).isEqualTo(state);
            assertThat(actual.getZip()).isEqualTo(zip);
            assertThat(actual.getPhoneNumber()).isEqualTo(phoneNumber);

            assertThat(actual.getRoles()).containsExactlyInAnyOrderElementsOf(roles);
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

            Collection<CassandraRoleProxy> newRoles = Stream
                .generate(() -> mock(CassandraRoleProxy.class))
                .limit(Generator.randomInt(50, 500))
                .collect(Collectors.toSet());

            CassandraUserProxy original = new CassandraUserProxy(new CassandraUserImpl(), userLoader)
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
                        .generate(() -> mock(CassandraRoleProxy.class))
                        .limit(Generator.randomInt(10, 20))
                        .collect(Collectors.toSet())
                );

            User actual = new CassandraUserBuilder(userProxyFactory, original)
                .userName(newUserName)
                .password(newPassword)
                .fullName(newFullName)
                .street(newStreet)
                .city(newCity)
                .state(newState)
                .zip(newZip)
                .phoneNumber(newPhoneNumber)
                .roles(newRoles)
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
        });
    }

    @Test
    void buildWithOriginal_thenReturnOriginalWithoutModifiedValuesAndKeepingSameValues() {
        assertDoesNotThrow(() -> {

            CassandraUserProxy original = new CassandraUserProxy(new CassandraUserImpl(), userLoader)
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
                        .generate(() -> mock(CassandraRoleProxy.class))
                        .limit(Generator.randomInt(10, 20))
                        .collect(Collectors.toSet())
                );

            User actual = new CassandraUserBuilder(userProxyFactory, original).build();

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

            Collection<CassandraRoleProxy> roles = Stream
                .generate(() -> mock(CassandraRoleProxy.class))
                .limit(Generator.randomInt(50, 500))
                .collect(Collectors.toSet());

            when(userProxyFactory.create(any(CassandraUserImpl.class)))
                .thenAnswer(invocationOnMock -> {
                    Object methodArgument = invocationOnMock.getArgument(0);

                    assertThat(methodArgument).isInstanceOf(CassandraUserImpl.class);

                    return new CassandraUserProxy((CassandraUserImpl) methodArgument, userLoader);
                });

            User actual = new CassandraUserBuilder(userProxyFactory, null)
                .userName(userName)
                .password(password)
                .fullName(fullName)
                .street(street)
                .city(city)
                .state(state)
                .zip(zip)
                .phoneNumber(phoneNumber)
                .roles(roles)
                .build();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(CassandraUserProxy.class);

            assertThat(actual.getId()).isNull();

            assertThat(actual.getUserName()).isEqualTo(userName);
            assertThat(actual.getPassword()).isEqualTo(password);
            assertThat(actual.getFullName()).isEqualTo(fullName);

            assertThat(actual.getStreet()).isEqualTo(street);
            assertThat(actual.getCity()).isEqualTo(city);
            assertThat(actual.getState()).isEqualTo(state);
            assertThat(actual.getZip()).isEqualTo(zip);
            assertThat(actual.getPhoneNumber()).isEqualTo(phoneNumber);

            assertThat(actual.getRoles()).containsExactlyInAnyOrderElementsOf(roles);
        });
    }
}
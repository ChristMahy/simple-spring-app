package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.builder;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraRole;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader.provider.UserLoaderProvider;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.*;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraRoleProxyFactory;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.provider.CassandraUserProxyFactoryProvider;
import cmahy.simple.spring.webapp.user.kernel.domain.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CassandraRoleBuilderTest {

    @Mock
    private CassandraRoleProxyFactory roleProxyFactory;

    @Mock
    private UserLoaderProvider userLoaderProvider;

    @Mock
    private CassandraUserProxyFactoryProvider factoryProvider;

    @Test
    void build() {
        assertDoesNotThrow(() -> {

            String name = Generator.generateAString();
            List<CassandraUserProxy> users = Stream
                .generate(() -> mock(CassandraUserProxy.class))
                .limit(Generator.randomInt(50, 500))
                .toList();
            List<CassandraRightProxy> rights = Stream
                .generate(() -> mock(CassandraRightProxy.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            when(roleProxyFactory.create(any(CassandraRole.class)))
                .thenAnswer(invocationOnMock -> {
                    Object methodArgument = invocationOnMock.getArgument(0);

                    assertThat(methodArgument).isInstanceOf(CassandraRole.class);

                    return new CassandraRoleProxy((CassandraRole) methodArgument, userLoaderProvider, factoryProvider);
                });

            Role actual = new CassandraRoleBuilder(roleProxyFactory)
                .name(name)
                .users(users)
                .rights(rights)
                .build();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(CassandraRoleProxy.class);

            assertThat(actual.getId()).isNull();
            assertThat(actual.getName()).isEqualTo(name);
            assertThat(actual.getUsers()).containsExactlyElementsOf(users);
            assertThat(actual.getRights()).containsExactlyElementsOf(rights);

        });
    }

    @Test
    void buildWithOriginal_thenReturnOriginalWithModifiedValuesAndKeepingSameId() {
        assertDoesNotThrow(() -> {

            String newName = Generator.generateAString();
            List<CassandraUserProxy> newUsers = Stream
                .generate(() -> mock(CassandraUserProxy.class))
                .limit(Generator.randomInt(50, 500))
                .toList();
            List<CassandraRightProxy> newRights = Stream
                .generate(() -> mock(CassandraRightProxy.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            CassandraRoleProxy originalRole = new CassandraRoleProxy(
                new CassandraRole().setId(Generator.randomUUID()),
                userLoaderProvider,
                factoryProvider
            )
                .setName(Generator.generateAString(300))
                .setUsers(
                    Stream
                        .generate(() -> mock(CassandraUserProxy.class))
                        .limit(40)
                        .toList()
                )
                .setRights(
                    Stream
                        .generate(() -> mock(CassandraRightProxy.class))
                        .limit(40)
                        .toList()
                );

            Role actual = new CassandraRoleBuilder(roleProxyFactory, originalRole)
                .name(newName)
                .users(newUsers)
                .rights(newRights)
                .build();

            assertThat(actual)
                .isNotNull()
                .isSameAs(originalRole);

            assertThat(actual.getId()).isEqualTo(originalRole.getId());
            assertThat(actual.getName()).isEqualTo(newName);
            assertThat(actual.getUsers()).containsExactlyElementsOf(newUsers);
            assertThat(actual.getRights()).containsExactlyElementsOf(newRights);

        });
    }

    @Test
    void buildWithOriginal_thenReturnOriginalWithoutModifiedValuesAndKeepingSameValues() {
        assertDoesNotThrow(() -> {

            CassandraRoleProxy originalRole = new CassandraRoleProxy(
                new CassandraRole().setId(Generator.randomUUID()),
                userLoaderProvider,
                factoryProvider
            )
                .setName(Generator.generateAString(300))
                .setUsers(
                    Stream
                        .generate(() -> mock(CassandraUserProxy.class))
                        .limit(40)
                        .toList()
                )
                .setRights(
                    Stream
                        .generate(() -> mock(CassandraRightProxy.class))
                        .limit(40)
                        .toList()
                );

            Role actual = new CassandraRoleBuilder(roleProxyFactory, originalRole).build();

            assertThat(actual)
                .isNotNull()
                .isSameAs(originalRole);

            assertThat(actual.getId()).isEqualTo(originalRole.getId());
            assertThat(actual.getName()).isEqualTo(originalRole.getName());
            assertThat(actual.getUsers()).containsExactlyElementsOf(originalRole.getUsers());
            assertThat(actual.getRights()).containsExactlyElementsOf(originalRole.getRights());

        });
    }

    @Test
    void buildWithNullAsOriginal_thenBuildNewOne() {
        assertDoesNotThrow(() -> {
            String newName = Generator.generateAString();
            List<CassandraUserProxy> newUsers = Stream
                .generate(() -> mock(CassandraUserProxy.class))
                .limit(Generator.randomInt(50, 500))
                .toList();
            List<CassandraRightProxy> newRights = Stream
                .generate(() -> mock(CassandraRightProxy.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            when(roleProxyFactory.create(any(CassandraRole.class)))
                .thenAnswer(invocationOnMock -> {
                    Object methodArgument = invocationOnMock.getArgument(0);

                    assertThat(methodArgument).isInstanceOf(CassandraRole.class);

                    return new CassandraRoleProxy((CassandraRole) methodArgument, userLoaderProvider, factoryProvider);
                });

            Role actual = new CassandraRoleBuilder(roleProxyFactory, null)
                .name(newName)
                .users(newUsers)
                .rights(newRights)
                .build();

            assertThat(actual).isNotNull();

            assertThat(actual.getId()).isNull();
            assertThat(actual.getName()).isEqualTo(newName);
            assertThat(actual.getUsers()).containsExactlyElementsOf(newUsers);
            assertThat(actual.getRights()).containsExactlyElementsOf(newRights);
        });
    }
}
package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.builder;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraRole;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader.RoleLoader;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraRoleProxy;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraUserProxy;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraRoleProxyFactory;
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
    private RoleLoader roleLoader;

    @Test
    void build() {
        assertDoesNotThrow(() -> {
            String name = Generator.generateAString();
            List<CassandraUserProxy> users = Stream
                .generate(() -> mock(CassandraUserProxy.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            when(roleProxyFactory.create(any(CassandraRole.class)))
                .thenAnswer(invocationOnMock -> {
                    Object methodArgument = invocationOnMock.getArgument(0);

                    assertThat(methodArgument).isInstanceOf(CassandraRole.class);

                    return new CassandraRoleProxy((CassandraRole) methodArgument, roleLoader);
                });

            Role actual = new CassandraRoleBuilder(roleProxyFactory)
                .name(name)
                .users(users)
                .build();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(CassandraRoleProxy.class);

            assertThat(actual.getId()).isNull();
            assertThat(actual.getName()).isEqualTo(name);
            assertThat(actual.getUsers()).containsExactlyElementsOf(users);
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

            CassandraRoleProxy originalRole = new CassandraRoleProxy(
                new CassandraRole().setId(Generator.randomUUID()),
                roleLoader
            )
                .setName(Generator.generateAString(300))
                .setUsers(
                    Stream
                        .generate(() -> mock(CassandraUserProxy.class))
                        .limit(40)
                        .toList()
                );

            Role actual = new CassandraRoleBuilder(roleProxyFactory, originalRole)
                .name(newName)
                .users(newUsers)
                .build();

            assertThat(actual)
                .isNotNull()
                .isSameAs(originalRole);

            assertThat(actual.getId()).isEqualTo(originalRole.getId());
            assertThat(actual.getName()).isEqualTo(newName);
            assertThat(actual.getUsers()).containsExactlyElementsOf(newUsers);
        });
    }

    @Test
    void buildWithOriginal_thenReturnOriginalWithoutModifiedValuesAndKeepingSameValues() {
        assertDoesNotThrow(() -> {

            CassandraRoleProxy originalRole = new CassandraRoleProxy(
                new CassandraRole().setId(Generator.randomUUID()),
                roleLoader
            )
                .setName(Generator.generateAString(300))
                .setUsers(
                    Stream
                        .generate(() -> mock(CassandraUserProxy.class))
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

            when(roleProxyFactory.create(any(CassandraRole.class)))
                .thenAnswer(invocationOnMock -> {
                    Object methodArgument = invocationOnMock.getArgument(0);

                    assertThat(methodArgument).isInstanceOf(CassandraRole.class);

                    return new CassandraRoleProxy((CassandraRole) methodArgument, roleLoader);
                });

            Role actual = new CassandraRoleBuilder(roleProxyFactory, null)
                .name(newName)
                .users(newUsers)
                .build();

            assertThat(actual).isNotNull();

            assertThat(actual.getId()).isNull();
            assertThat(actual.getName()).isEqualTo(newName);
            assertThat(actual.getUsers()).containsExactlyElementsOf(newUsers);
        });
    }
}
package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.builder;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraRight;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader.provider.UserLoaderProvider;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraRightProxy;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraRoleProxy;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraRightProxyFactory;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.provider.CassandraUserProxyFactoryProvider;
import cmahy.simple.spring.webapp.user.kernel.domain.Right;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CassandraRightBuilderTest {

    @Mock
    private CassandraRightProxyFactory rightProxyFactory;

    @Mock
    private UserLoaderProvider userLoaderProvider;

    @Mock
    private CassandraUserProxyFactoryProvider factoryProvider;

    @Test
    void build() {
        assertDoesNotThrow(() -> {

            String name = Generator.generateAString();
            List<CassandraRoleProxy> roles = Stream
                .generate(() -> mock(CassandraRoleProxy.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            when(rightProxyFactory.create(any(CassandraRight.class)))
                .thenAnswer(invocationOnMock -> {
                    Object methodArg = invocationOnMock.getArgument(0);

                    assertThat(methodArg).isInstanceOf(CassandraRight.class);

                    return new CassandraRightProxy((CassandraRight) methodArg, userLoaderProvider, factoryProvider);
                });


            Right actual = new CassandraRightBuilder(rightProxyFactory)
                .name(name)
                .roles(roles)
                .build();


            assertThat(actual)
                .isNotNull()
                .isInstanceOf(CassandraRightProxy.class);

            assertThat(actual.getId()).isNull();
            assertThat(actual.getName()).isEqualTo(name);
            assertThat(actual.getRoles()).containsExactlyElementsOf(roles);

        });
    }

    @Test
    void buildWithOriginal_thenReturnOriginalWithModifiedValuesAndKeepingSameId() {
        assertDoesNotThrow(() -> {

            String newName = Generator.generateAString();
            List<CassandraRoleProxy> newRoles = Stream
                .generate(() -> mock(CassandraRoleProxy.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            CassandraRightProxy originalRight = new CassandraRightProxy(
                new CassandraRight().setId(Generator.randomUUID()),
                userLoaderProvider,
                factoryProvider
            )
                .setName(Generator.generateAString())
                .setRoles(
                    Stream
                        .generate(() -> mock(CassandraRoleProxy.class))
                        .limit(40)
                        .collect(Collectors.toSet())
                );


            Right actual = new CassandraRightBuilder(rightProxyFactory, originalRight)
                .name(newName)
                .roles(newRoles)
                .build();


            assertThat(actual)
                .isNotNull()
                .isInstanceOf(CassandraRightProxy.class);

            assertThat(actual.getId()).isEqualTo(originalRight.getId());
            assertThat(actual.getName()).isEqualTo(newName);
            assertThat(actual.getRoles()).containsExactlyElementsOf(newRoles);

        });
    }

    @Test
    void buildWithOriginal_thenReturnOriginalWithoutModifiedValuesAndKeepingSameValues() {
        assertDoesNotThrow(() -> {

            CassandraRightProxy originalRight = new CassandraRightProxy(
                new CassandraRight().setId(Generator.randomUUID()),
                userLoaderProvider,
                factoryProvider
            )
                .setName(Generator.generateAString())
                .setRoles(
                    Stream
                        .generate(() -> mock(CassandraRoleProxy.class))
                        .limit(40)
                        .collect(Collectors.toSet())
                );


            Right actual = new CassandraRightBuilder(rightProxyFactory, originalRight).build();


            assertThat(actual)
                .isNotNull()
                .isInstanceOf(CassandraRightProxy.class);

            assertThat(actual.getId()).isEqualTo(originalRight.getId());
            assertThat(actual.getName()).isEqualTo(originalRight.getName());
            assertThat(actual.getRoles()).containsExactlyElementsOf(originalRight.getRoles());

        });
    }

    @Test
    void buildWithNullAsOriginal_thenBuildNewOne() {
        assertDoesNotThrow(() -> {

            String name = Generator.generateAString();
            List<CassandraRoleProxy> roles = Stream
                .generate(() -> mock(CassandraRoleProxy.class))
                .limit(Generator.randomInt(50, 500))
                .toList();

            when(rightProxyFactory.create(any(CassandraRight.class)))
                .thenAnswer(invocationOnMock -> {
                    Object methodArg = invocationOnMock.getArgument(0);

                    assertThat(methodArg).isInstanceOf(CassandraRight.class);

                    return new CassandraRightProxy((CassandraRight) methodArg, userLoaderProvider, factoryProvider);
                });


            Right actual = new CassandraRightBuilder(rightProxyFactory, null)
                .name(name)
                .roles(roles)
                .build();


            assertThat(actual)
                .isNotNull()
                .isInstanceOf(CassandraRightProxy.class);

            assertThat(actual.getId()).isNull();
            assertThat(actual.getName()).isEqualTo(name);
            assertThat(actual.getRoles()).containsExactlyElementsOf(roles);

        });
    }
}
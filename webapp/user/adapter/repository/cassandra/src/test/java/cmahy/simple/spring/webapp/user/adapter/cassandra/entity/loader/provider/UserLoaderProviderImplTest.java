package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader.provider;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.*;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader.Loader;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserLoaderProviderImplTest {

    private UserLoaderProviderImpl userLoaderProviderImpl;

    @Test
    void resolve() {
        assertDoesNotThrow(() -> {

            List<Class<?>> availableEntities = List.of(
                CassandraRight.class,
                CassandraRole.class,
                CassandraUserImpl.class,
                CassandraUserSecurityImpl.class
            );

            List<Loader> loaders = availableEntities.stream()
                .map(entityClass -> {
                    Loader loader = mock(Loader.class);

                    when(loader.entityClass()).thenAnswer(_ -> entityClass);

                    return loader;
                })
                .toList();

            userLoaderProviderImpl = new UserLoaderProviderImpl(loaders);

            int randomPosition = Generator.randomInt(0, availableEntities.size() - 1);


            Loader actual = userLoaderProviderImpl.resolve(loaders.get(randomPosition).entityClass());


            assertThat(actual)
                .isNotNull()
                .isSameAs(loaders.get(randomPosition));

        });
    }

    @Test
    void resolve_whenEntityClassNotFound_thenThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {

            List<Class<?>> availableEntities = List.of(
                CassandraRight.class,
                CassandraRole.class,
                CassandraUserImpl.class,
                CassandraUserSecurityImpl.class
            );

            List<Loader> loaders = availableEntities.stream()
                .map(entityClass -> {
                    Loader loader = mock(Loader.class);

                    when(loader.entityClass()).thenAnswer(_ -> entityClass);

                    return loader;
                })
                .toList();

            userLoaderProviderImpl = new UserLoaderProviderImpl(loaders);


            userLoaderProviderImpl.resolve(Object.class);

        });
    }

    @Test
    void newInstance_whenClassWithoutExpectedAnnotation_thenThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {

            List<Class<?>> availableEntities = new ArrayList<>() {{
                add(CassandraRight.class);
                add(CassandraRole.class);
                add(CassandraUserImpl.class);
                add(CassandraUserSecurityImpl.class);
                add(Object.class);
            }};

            Collections.shuffle(availableEntities);

            List<Loader> loaders = availableEntities.stream()
                .map(entityClass -> {
                    Loader loader = mock(Loader.class);

                    when(loader.entityClass()).thenAnswer(_ -> entityClass);

                    return loader;
                })
                .toList();

            userLoaderProviderImpl = new UserLoaderProviderImpl(loaders);

        });
    }
}
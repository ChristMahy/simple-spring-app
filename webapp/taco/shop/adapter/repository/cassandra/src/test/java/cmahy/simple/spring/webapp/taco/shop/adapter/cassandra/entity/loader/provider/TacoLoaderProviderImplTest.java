package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.loader.provider;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.domain.*;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.loader.Loader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TacoLoaderProviderImplTest {

    private TacoLoaderProviderImpl tacoLoaderProviderImpl;

    @Test
    void resolve() {
        assertDoesNotThrow(() -> {

            List<Class<?>> availablesEntities = List.of(
                CassandraIngredient.class,
                CassandraTaco.class,
                CassandraClientOrder.class
            );

            List<Loader> loaders = availablesEntities.stream()
                .map(entityClass -> {
                    Loader loader = mock(Loader.class);

                    when(loader.entityClass()).thenAnswer(_ -> entityClass);

                    return loader;
                })
                .toList();

            tacoLoaderProviderImpl = new TacoLoaderProviderImpl(loaders);

            int randomPosition = Generator.randomInt(0, availablesEntities.size() - 1);


            Loader actual = tacoLoaderProviderImpl.resolve(loaders.get(randomPosition).entityClass());


            assertThat(actual)
                .isNotNull()
                .isSameAs(loaders.get(randomPosition));
        });
    }

    @Test
    void resolve_whenEntityClassNotFound_thenThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {

            List<Class<?>> availablesEntities = List.of(
                CassandraIngredient.class,
                CassandraTaco.class,
                CassandraClientOrder.class
            );

            List<Loader> loaders = availablesEntities.stream()
                .map(entityClass -> {
                    Loader loader = mock(Loader.class);

                    when(loader.entityClass()).thenAnswer(_ -> entityClass);

                    return loader;
                })
                .toList();

            tacoLoaderProviderImpl = new TacoLoaderProviderImpl(loaders);


            tacoLoaderProviderImpl.resolve(Object.class);
        });
    }

    @Test
    void newInstance_whenClassWithoutExpectedAnnotation_thenThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {

            List<Class<?>> availablesEntities = new ArrayList<>() {{
                add(CassandraIngredient.class);
                add(CassandraTaco.class);
                add(CassandraClientOrder.class);
                add(Object.class);
            }};

            Collections.shuffle(availablesEntities);

            List<Loader> loaders = availablesEntities.stream()
                .map(entityClass -> {
                    Loader loader = mock(Loader.class);

                    when(loader.entityClass()).thenAnswer(_ -> entityClass);

                    return loader;
                })
                .toList();

            tacoLoaderProviderImpl = new TacoLoaderProviderImpl(loaders);
        });
    }
}
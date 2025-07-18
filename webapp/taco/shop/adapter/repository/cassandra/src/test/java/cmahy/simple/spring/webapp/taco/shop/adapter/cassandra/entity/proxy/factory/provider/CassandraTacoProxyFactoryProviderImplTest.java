package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.provider;

import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.domain.*;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class CassandraTacoProxyFactoryProviderImplTest {

    @InjectMocks
    private CassandraTacoProxyFactoryProviderImpl cassandraTacoProxyFactoryProviderImpl;

    @ParameterizedTest
    @MethodSource("classeWhichExpectesAFactory")
    void resolve(Class classeWhichExpectesAFactory, Class expectedFactory) {
        assertDoesNotThrow(() -> {

            assertThat(cassandraTacoProxyFactoryProviderImpl.resolve(classeWhichExpectesAFactory)).isInstanceOf(expectedFactory);

        });
    }

    private static Stream<Arguments> classeWhichExpectesAFactory() {
        return Stream.of(
            Arguments.of(CassandraIngredient.class, CassandraIngredientProxyFactory.class),
            Arguments.of(CassandraTaco.class, CassandraTacoProxyFactory.class),
            Arguments.of(CassandraClientOrder.class, CassandraClientOrderProxyFactory.class)
        );
    }

    @Test
    void resolve_whenEntityNotFound_thenThrowAnError() {
        assertThrows(IllegalArgumentException.class, () -> {

            cassandraTacoProxyFactoryProviderImpl.resolve(Object.class);

        });
    }
}
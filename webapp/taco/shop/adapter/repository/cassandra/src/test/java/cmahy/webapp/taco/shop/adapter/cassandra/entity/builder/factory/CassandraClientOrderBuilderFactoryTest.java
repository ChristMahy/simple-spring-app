package cmahy.webapp.taco.shop.adapter.cassandra.entity.builder.factory;

import cmahy.webapp.taco.shop.adapter.cassandra.entity.builder.CassandraClientOrderBuilder;
import cmahy.webapp.taco.shop.adapter.cassandra.entity.proxy.CassandraClientOrderProxy;
import cmahy.webapp.taco.shop.kernel.domain.ClientOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Answers.RETURNS_SELF;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class CassandraClientOrderBuilderFactoryTest {

    @InjectMocks
    private CassandraClientOrderBuilderFactory cassandraClientOrderBuilderFactory;

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            CassandraClientOrderBuilder actual = cassandraClientOrderBuilderFactory.create();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(CassandraClientOrderBuilder.class);
        });
    }

    @Test
    void createWithOriginal() {
        assertDoesNotThrow(() -> {
            CassandraClientOrderProxy clientOrder = mock(CassandraClientOrderProxy.class, RETURNS_SELF);

            CassandraClientOrderBuilder actual = cassandraClientOrderBuilderFactory.create(clientOrder);

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(CassandraClientOrderBuilder.class);

            ClientOrder actualBuilderResult = actual.build();

            assertThat(actualBuilderResult).isSameAs(clientOrder);
        });
    }
}
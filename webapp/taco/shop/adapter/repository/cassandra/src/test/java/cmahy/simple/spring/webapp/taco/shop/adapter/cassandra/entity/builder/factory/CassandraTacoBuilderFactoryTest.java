package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.builder.factory;

import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.builder.CassandraTacoBuilder;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.CassandraTacoProxy;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Taco;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Answers.RETURNS_SELF;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class CassandraTacoBuilderFactoryTest {

    @InjectMocks
    private CassandraTacoBuilderFactory cassandraTacoBuilderFactory;

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            CassandraTacoBuilder actual = cassandraTacoBuilderFactory.create();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(CassandraTacoBuilder.class);
        });
    }

    @Test
    void createWithOriginal() {
        assertDoesNotThrow(() -> {
            CassandraTacoProxy original = mock(CassandraTacoProxy.class, RETURNS_SELF);

            CassandraTacoBuilder actual = cassandraTacoBuilderFactory.create(original);

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(CassandraTacoBuilder.class);

            Taco actualBuilderResult = actual.build();

            assertThat(actualBuilderResult).isSameAs(original);
        });
    }
}
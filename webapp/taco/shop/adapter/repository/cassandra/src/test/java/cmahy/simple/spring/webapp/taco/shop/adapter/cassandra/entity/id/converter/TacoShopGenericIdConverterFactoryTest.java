package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.id.converter;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class TacoShopGenericIdConverterFactoryTest {

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            assertThat(TacoShopGenericIdConverterFactory.create()).isNotNull();
        });
    }

}
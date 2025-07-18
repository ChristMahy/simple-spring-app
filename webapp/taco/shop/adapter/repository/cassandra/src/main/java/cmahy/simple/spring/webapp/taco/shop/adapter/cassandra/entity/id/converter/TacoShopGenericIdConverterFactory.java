package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.id.converter;

import java.util.Collection;
import java.util.Set;

public final class TacoShopGenericIdConverterFactory {

    private TacoShopGenericIdConverterFactory() {}

    public static Collection<Object> create() {
        return Set.of(
            new TacoGenericIdReadingConverter(),
            new TacoGenericIdWritingConverter()
        );
    }
}

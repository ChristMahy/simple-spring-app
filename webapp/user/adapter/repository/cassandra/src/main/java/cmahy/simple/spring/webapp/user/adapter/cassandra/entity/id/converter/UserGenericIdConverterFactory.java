package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.id.converter;

import java.util.Collection;
import java.util.Set;

public final class UserGenericIdConverterFactory {

    private UserGenericIdConverterFactory() {}

    public static Collection<Object> create() {
        return Set.of(
            new UserGenericIdReadingConverter(),
            new UserGenericIdWritingConverter()
        );
    }
}

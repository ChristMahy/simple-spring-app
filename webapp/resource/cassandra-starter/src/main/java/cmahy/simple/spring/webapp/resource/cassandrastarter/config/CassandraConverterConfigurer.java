package cmahy.simple.spring.webapp.resource.cassandrastarter.config;

import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.id.converter.TacoShopGenericIdConverterFactory;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.id.converter.UserGenericIdConverterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.core.convert.CassandraCustomConversions;

import java.util.List;
import java.util.stream.Stream;

@Configuration
public class CassandraConverterConfigurer {

    @Bean
    public CassandraCustomConversions cassandraCustomConversions() {
        return CassandraCustomConversions.create(configurer -> {
            List<Object> allCassandraConverter = Stream.of(
                    TacoShopGenericIdConverterFactory.create().stream(),
                    UserGenericIdConverterFactory.create().stream()
                )
                .flatMap(converter -> converter)
                .toList();

            configurer.registerConverters(allCassandraConverter);
        });
    }

}

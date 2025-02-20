package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.config;

import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.id.codec.type.IngredentIdTypeCodec;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.id.codec.type.TacoIdTypeCodec;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TacoShopCodecConfigurer {

    @Bean
    public CqlSessionBuilderCustomizer addTacoShopTypeCodecs() {
        return cqlSessionBuilder -> cqlSessionBuilder
            .addTypeCodecs(
                new IngredentIdTypeCodec(),
                new TacoIdTypeCodec()
            );
    }
}

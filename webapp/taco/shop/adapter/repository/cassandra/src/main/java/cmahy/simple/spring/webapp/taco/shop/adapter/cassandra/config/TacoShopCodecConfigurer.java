package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.config;

import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.id.codec.type.IngredentIdTypeCodec;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.id.codec.type.TacoIdTypeCodec;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.SessionBuilderConfigurer;

@Configuration
public class TacoShopCodecConfigurer {

    @Bean
    public SessionBuilderConfigurer addTacoShopTypeCodecs() {
        return sessionBuilder -> sessionBuilder
            .addTypeCodecs(
                new IngredentIdTypeCodec(),
                new TacoIdTypeCodec()
            );
    }

}

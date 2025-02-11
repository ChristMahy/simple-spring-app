package cmahy.webapp.user.adapter.cassandra.config;

import cmahy.webapp.user.adapter.cassandra.entity.id.codec.type.RoleIdTypeCodec;
import cmahy.webapp.user.adapter.cassandra.entity.id.codec.type.UserIdTypeCodec;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserCodecConfigurer {

    @Bean
    public CqlSessionBuilderCustomizer addUserTypeCodecs() {
        return sessionBuilder -> sessionBuilder
            .addTypeCodecs(
                new RoleIdTypeCodec(),
                new UserIdTypeCodec()
            );
    }
}

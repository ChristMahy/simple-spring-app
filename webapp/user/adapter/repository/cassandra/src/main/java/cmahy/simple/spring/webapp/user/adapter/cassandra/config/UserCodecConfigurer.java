package cmahy.simple.spring.webapp.user.adapter.cassandra.config;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.id.codec.type.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.SessionBuilderConfigurer;

@Configuration
public class UserCodecConfigurer {

    @Bean
    public SessionBuilderConfigurer addUserTypeCodecs() {
        return sessionBuilder -> sessionBuilder
            .addTypeCodecs(
                new RoleIdTypeCodec(),
                new UserIdTypeCodec(),
                new RightIdTypeCodec()
            );
    }

}

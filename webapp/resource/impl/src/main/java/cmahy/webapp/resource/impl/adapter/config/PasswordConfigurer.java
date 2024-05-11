package cmahy.webapp.resource.impl.adapter.config;

import cmahy.webapp.resource.impl.adapter.config.properties.PasswordProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PasswordConfigurer {

    @Bean
    public PasswordEncoder passwordEncoder(PasswordProperties passwordProperties) throws Exception {
        Map<String, PasswordEncoder> encoders = new HashMap<>();

        for (Map.Entry<String, String> entry : passwordProperties.encoders().entrySet()) {
            encoders.put(entry.getKey(), (PasswordEncoder) Class.forName(entry.getValue()).getDeclaredConstructor().newInstance());
        }

        return new DelegatingPasswordEncoder(
            passwordProperties.defaultEncoder(),
            encoders
        );
    }
}

package cmahy.springapp.resourceserver.config.security;

import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static cmahy.springapp.resourceserver.config.security.HttpSecurityConfig.ORDER_CONFIG_LOGOUT;
import static org.slf4j.LoggerFactory.getLogger;

@Configuration
public class LogoutHttpSecurityConfig {
    private static final Logger LOG = getLogger(LogoutHttpSecurityConfig.class);

    @Bean
    @Order(ORDER_CONFIG_LOGOUT)
    public SecurityFilterChain logoutHttpSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        LOG.info("Setup logout configuration");

        return httpSecurity
            .logout()
            .logoutSuccessUrl("/")
            .and()
            .build();
    }
}

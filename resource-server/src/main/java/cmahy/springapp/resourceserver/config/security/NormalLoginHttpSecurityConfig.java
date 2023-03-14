package cmahy.springapp.resourceserver.config.security;

import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static cmahy.springapp.resourceserver.config.security.HttpSecurityConfig.ORDER_CONFIG_NORMAL_LOGIN;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
public class NormalLoginHttpSecurityConfig {
    private static final Logger LOG = getLogger(NormalLoginHttpSecurityConfig.class);

    @Bean
    @Order(ORDER_CONFIG_NORMAL_LOGIN)
    public SecurityFilterChain normalLoginHttpSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        LOG.info("Setup normal login configuration");

        return httpSecurity
            .csrf().ignoringRequestMatchers(toH2Console())
            .and()
            .headers()
            .frameOptions().sameOrigin()
            .and()
            .formLogin()
            .loginPage("/login")
            .and()
            .build();
    }
}

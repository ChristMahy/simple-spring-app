package cmahy.springapp.resourceserver.config.security;

import org.slf4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

import static cmahy.springapp.resourceserver.config.security.HttpSecurityConfig.ORDER_CONFIG_LOGOUT;
import static org.slf4j.LoggerFactory.getLogger;

@Component
@Order(ORDER_CONFIG_LOGOUT)
public class LogoutHttpSecurityConfig implements HttpSecurityConfig {
    private static final Logger LOG = getLogger(LogoutHttpSecurityConfig.class);

    @Override
    public HttpSecurity configure(HttpSecurity httpSecurity) throws Exception {
        LOG.info("{}, setup logout configuration", HttpSecurityConfig.class.getSimpleName());

        return httpSecurity
            .logout()
            .logoutSuccessUrl("/")
            .and();
    }
}

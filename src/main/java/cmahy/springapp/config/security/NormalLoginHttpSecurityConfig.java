package cmahy.springapp.config.security;

import org.slf4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

import static cmahy.springapp.config.security.HttpSecurityConfig.ORDER_CONFIG_NORMAL_LOGIN;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Component
@Order(ORDER_CONFIG_NORMAL_LOGIN)
public class NormalLoginHttpSecurityConfig implements HttpSecurityConfig {
    private static final Logger LOG = getLogger(NormalLoginHttpSecurityConfig.class);

    @Override
    public HttpSecurity configure(HttpSecurity httpSecurity) throws Exception {
        LOG.info("{}, setup normal login configuration", HttpSecurityConfig.class.getSimpleName());
        return httpSecurity
            .csrf().ignoringRequestMatchers(toH2Console())
            .and()
            .headers()
            .frameOptions().sameOrigin()
            .and()
            .formLogin()
            .loginPage("/login")
            .and();
    }
}

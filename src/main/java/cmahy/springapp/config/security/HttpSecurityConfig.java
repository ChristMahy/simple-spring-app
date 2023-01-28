package cmahy.springapp.config.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public interface HttpSecurityConfig {
    int ORDER_CONFIG_NORMAL_LOGIN = 1;
    int ORDER_CONFIG_OAUTH2_LOGIN = 2;
    int ORDER_CONFIG_LOGOUT = 3;

    HttpSecurity configure(HttpSecurity httpSecurity) throws Exception;
}

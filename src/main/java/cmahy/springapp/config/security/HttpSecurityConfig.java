package cmahy.springapp.config.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public interface HttpSecurityConfig {
    HttpSecurity execute(HttpSecurity httpSecurity) throws Exception;
}

package cmahy.springapp.config.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

@Component
public class DefaultHttpSecurityConfig implements HttpSecurityConfig {
    @Override
    public HttpSecurity execute(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .formLogin()
            .loginPage("/login")
            .and()
            .headers()
            .frameOptions().sameOrigin()
            .and()
            .logout()
            .logoutSuccessUrl("/")
            .and();
    }
}

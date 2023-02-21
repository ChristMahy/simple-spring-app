package cmahy.springapp.authorizationserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(
        HttpSecurity httpSecurity
    ) throws Exception {
        return httpSecurity
            .authorizeHttpRequests(authorizationRequest ->
                authorizationRequest.anyRequest().authenticated()
            )
            .formLogin()
            .and().build();
    }
}

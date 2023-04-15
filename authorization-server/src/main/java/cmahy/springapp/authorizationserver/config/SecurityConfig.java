package cmahy.springapp.authorizationserver.config;

import cmahy.springapp.authorizationserver.service.GetUserCredentialsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(GetUserCredentialsService credentialsService) {
        return credentialsService::execute;
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
            .csrf().ignoringRequestMatchers(toH2Console())
            .and()
            .headers()
            .frameOptions().sameOrigin()
            .and()
            .formLogin()
            .and()
            .build();
    }
}

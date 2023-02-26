package cmahy.springapp.authorizationserver.config;

import cmahy.springapp.authorizationserver.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
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
            .csrf().ignoringRequestMatchers(toH2Console())
            .and()
            .headers()
            .frameOptions().sameOrigin()
            .and()
            .formLogin()
            .and().build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> userRepository.findByUsername(username)
            .map(UserSecurityDetails::new)
            .orElseThrow(() -> new UsernameNotFoundException(
                "User '" + username + "' not found"
            ));
    }
}

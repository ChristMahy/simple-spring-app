package cmahy.simple.spring.webapp.authorization.adapter.config;

import cmahy.simple.spring.webapp.authorization.adapter.mapper.output.UserSecurityDetailsMapper;
import cmahy.simple.spring.webapp.authorization.application.query.GetUserByUsernameQuery;
import cmahy.simple.spring.webapp.authorization.exception.UserNotFoundException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
public class SecurityConfigurer {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(
        GetUserByUsernameQuery getUserByUsernameQuery,
        UserSecurityDetailsMapper userSecurityDetailsMapper
    ) {
        return username -> {
            try {
                return userSecurityDetailsMapper.map(
                    getUserByUsernameQuery.execute(username)
                );
            } catch (UserNotFoundException notFound) {
                throw new UsernameNotFoundException(notFound.getMessage());
            }
        };
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().fullyAuthenticated())
            .csrf(csrfConfigurer -> {
                csrfConfigurer
                    .ignoringRequestMatchers(toH2Console());
            })
            .headers(headersConfigurer -> {
                headersConfigurer
                    .frameOptions(
                        HeadersConfigurer.FrameOptionsConfig::sameOrigin
                    );
            })
            .formLogin(Customizer.withDefaults())
            .build();
    }
}

package cmahy.springapp.resourceserver.config.security;

import cmahy.springapp.resourceserver.service.security.OAuth2ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    private static final Logger LOG = getLogger(SecurityConfig.class);

    private final OAuth2ServiceImpl oAuth2Service;

    public SecurityConfig(OAuth2ServiceImpl oAuth2Service) {
        this.oAuth2Service = oAuth2Service;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Order
    public SecurityFilterChain securityFilterChain(
        HttpSecurity httpSecurity
    ) throws Exception {
        LOG.info("Setup normal login configuration");

        httpSecurity
            .csrf().ignoringRequestMatchers(toH2Console());

        httpSecurity
            .headers()
            .frameOptions().sameOrigin();

        LOG.info("Setup logout configuration");

        httpSecurity
            .logout(customizer -> customizer
                .logoutSuccessUrl("/")
            );

        httpSecurity
            .formLogin(customizer -> customizer
                .loginPage("/login")
            );

        httpSecurity
            .oauth2Login(customizer -> customizer
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(oAuth2Service)
            );

        httpSecurity
            .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);

        LOG.info("Setup default security configuration");

        return httpSecurity.build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return Jackson2ObjectMapperBuilder.json()
            .modules()
            .build();
    }
}

package cmahy.springapp.resourceserver.config.security;

import cmahy.springapp.resourceserver.service.security.OAuth2ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static cmahy.springapp.resourceserver.controller.rest.ApiUrlConstant.IntegrationUrl.BASIC_INTEGRATION_URL;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toStaticResources;

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
    public SecurityFilterChain securityFilterChain(
        HttpSecurity httpSecurity
    ) throws Exception {

        LOG.info("Setup normal login configuration");

        httpSecurity
            .csrf(AbstractHttpConfigurer::disable);

        httpSecurity.sessionManagement(sessionConfigurer ->
            sessionConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        httpSecurity
            .headers(configurer -> configurer.frameOptions(
                HeadersConfigurer.FrameOptionsConfig::sameOrigin
            ));

        LOG.info("Setup logout configuration");

        httpSecurity
            .logout(configurer -> configurer.logoutSuccessUrl("/"));

        httpSecurity
            .formLogin(configurer -> configurer.loginPage("/login"));

        httpSecurity
            .oauth2Login(configurer -> configurer
                .loginPage("/login")
                .userInfoEndpoint(userInfoEndpointConfig ->
                    userInfoEndpointConfig.userService(oAuth2Service)
                )
            );

        httpSecurity
            .oauth2ResourceServer(configurer ->
                configurer.jwt(Customizer.withDefaults())
            );

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

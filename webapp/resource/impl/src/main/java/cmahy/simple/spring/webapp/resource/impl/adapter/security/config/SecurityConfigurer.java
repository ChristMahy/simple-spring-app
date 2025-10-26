package cmahy.simple.spring.webapp.resource.impl.adapter.security.config;

import cmahy.simple.spring.webapp.resource.impl.adapter.security.oauth2.TacoResourceOAuth2Service;
import cmahy.simple.spring.webapp.resource.impl.adapter.security.oidc.TacoResourceOidcService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Optional;

@Configuration
@EnableMethodSecurity
public class SecurityConfigurer {

    @Bean
    @ConditionalOnExpression(
        """
            '${spring.h2.console.enabled:false}' eq 'true' or
            '${spring.h2.console.enabled:false}' eq 'on' or
            '${spring.h2.console.enabled:false}' eq '1'
        """
    )
    public SecurityFilterChain h2ConsoleSecurityFilterChain(HttpSecurity http) throws Exception {
        PathRequest.H2ConsoleRequestMatcher h2ConsoleRequestMatcher = PathRequest.toH2Console();

        http
            .securityMatcher(h2ConsoleRequestMatcher)
            .authorizeHttpRequests(authorized -> authorized.anyRequest().permitAll())
            .csrf(csrf -> csrf.ignoringRequestMatchers(h2ConsoleRequestMatcher))
            .headers(headerConfigurer -> headerConfigurer.frameOptions(
                HeadersConfigurer.FrameOptionsConfig::sameOrigin
            ));

        return http.build();
    }

    @Bean
    public SecurityFilterChain errorPagesSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
            .securityMatcher("/error/**", "/access-denied/**")
            .authorizeHttpRequests(authorized -> authorized.anyRequest().permitAll())
            .anonymous(AbstractHttpConfigurer::disable)
            .cors(Customizer.withDefaults())
            .csrf(this::defaultCsrfConfigurer)
            .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.NEVER))
            .build();
    }

    @Bean
    public SecurityFilterChain exposePublicUrlsWithBasicSecurity(HttpSecurity http) throws Exception {
        return http
            .securityMatcher("", "/", "/without-controller", "/toggle-theme")
            .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
            .anonymous(AbstractHttpConfigurer::disable)
            .cors(Customizer.withDefaults())
            .csrf(this::defaultCsrfConfigurer)
            .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.NEVER))
            .build();
    }

    @Bean
    public SecurityFilterChain exposeAuthenticatorEndPoints(HttpSecurity http) throws Exception {
        return http
            .securityMatcher("/actuator", "/actuator/**")
            .authorizeHttpRequests(authorize -> authorize.anyRequest().hasRole("ADMIN"))
            .anonymous(AbstractHttpConfigurer::disable)
            .sessionManagement(this::defaultSessionConfigurer)
            .userDetailsService(this.inMemoryActuatorUserDetailsService())
            .httpBasic(Customizer.withDefaults())
            .build();
    }

    private UserDetailsService inMemoryActuatorUserDetailsService() {
        UserDetails user = User.builder()
            .username("actuator_test")
            .password("{bcrypt}$2a$10$/CToef.6weENA1KiYDCklefeYOE/ZWusaZGTCZyHss2ozfKtsDEp6")
            .roles("ADMIN")
            .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public SecurityFilterChain exposeRegisterUrls(HttpSecurity http) throws Exception {
        return http
            .securityMatcher("/register**")
            .authorizeHttpRequests(authorized -> authorized.anyRequest().permitAll())
            .anonymous(AbstractHttpConfigurer::disable)
            .cors(Customizer.withDefaults())
            .csrf(this::defaultCsrfConfigurer)
            .sessionManagement(this::defaultSessionConfigurer)
            .build();
    }

    @Bean
    public SecurityFilterChain exposeLoginUrls(
        HttpSecurity http,
        TacoResourceOAuth2Service tacoResourceOAuth2Service,
        TacoResourceOidcService tacoResourceOidcService,
        Optional<OAuth2ClientProperties> oAuth2ClientProperties
    ) throws Exception {
        HttpSecurity buildingHttpSecurity = http
            .securityMatcher(
                "/login", "/login/**",
                "/oauth2/authorization", "/oauth2/authorization/**"
            )
            .authorizeHttpRequests(authorized -> authorized.anyRequest().permitAll())
            .formLogin(configurer ->
                configurer
                    .loginPage("/login")
            );

        if (
            oAuth2ClientProperties
                .map(OAuth2ClientProperties::getRegistration)
                .filter(registrations -> !registrations.isEmpty())
                .isPresent()
        ) {
            buildingHttpSecurity
                .oauth2Login(configurer -> {
                    configurer
                        .loginPage("/login").permitAll()
                        .failureHandler(authenticationFailureHandler())
                        .userInfoEndpoint(userInfoEndpoint -> {
                            userInfoEndpoint.userService(tacoResourceOAuth2Service);
                            userInfoEndpoint.oidcUserService(tacoResourceOidcService);
                        });
                })
                .oauth2Client(Customizer.withDefaults());
        }

        buildingHttpSecurity
            .cors(Customizer.withDefaults())
            .csrf(this::defaultCsrfConfigurer)
            .sessionManagement(this::defaultSessionConfigurer);

        return http.build();
    }

    @Bean
    public SecurityFilterChain exposeLogoutUrls(HttpSecurity http) throws Exception {
        return http
            .securityMatcher("/logout", "/logout/**")
            .authorizeHttpRequests(authorized -> authorized.anyRequest().permitAll())
            .logout(configurer ->
                configurer
                    .logoutSuccessUrl("/")
                    .deleteCookies("JSESSIONID")
            )
            .cors(Customizer.withDefaults())
            .csrf(this::defaultCsrfConfigurer)
            .sessionManagement(this::defaultSessionConfigurer)
            .build();
    }

    @Bean
    public SecurityFilterChain exposeStaticUrls(HttpSecurity http) throws Exception {
        return http
            .securityMatcher(PathRequest.toStaticResources().atCommonLocations())
            .authorizeHttpRequests(registry -> registry.anyRequest().permitAll())
            .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.NEVER))
            .build();
    }

    @Bean
    public SecurityFilterChain allAPIOptionMethod(HttpSecurity http) throws Exception {
        return http
            .securityMatchers(
                configurer -> configurer.requestMatchers(HttpMethod.OPTIONS)
            )
            .authorizeHttpRequests(registry -> registry.anyRequest().permitAll())
            .build();
    }

    @Bean
    public SecurityFilterChain allowAPIUrls(
        HttpSecurity http,
        Optional<AuthenticationManagerResolver<HttpServletRequest>> authenticationManagerResolver
    ) throws Exception {
        http
            .securityMatchers(configurer -> configurer.requestMatchers("/api/**"))
            .authorizeHttpRequests(registry -> registry.anyRequest().fullyAuthenticated())
            .anonymous(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .cors(Customizer.withDefaults())
            .csrf(CsrfConfigurer::disable)
            .httpBasic(Customizer.withDefaults());

        if (authenticationManagerResolver.isPresent()) {
            http.oauth2ResourceServer(configurer -> {
                configurer
                    .authenticationManagerResolver(authenticationManagerResolver.get());
            });
        }

        return http.build();
    }

    @Bean
    public SecurityFilterChain anyLeftUnsecureUrls(
        HttpSecurity http
    ) throws Exception {
        return http
            .authorizeHttpRequests(authorize -> authorize.anyRequest().fullyAuthenticated())
            .anonymous(AbstractHttpConfigurer::disable)
            .csrf(this::defaultCsrfConfigurer)
            .cors(Customizer.withDefaults())
            .sessionManagement(this::defaultSessionConfigurer)
            .exceptionHandling(exceptionHandler ->
                exceptionHandler
                    .authenticationEntryPoint((request, response, authenticationException) ->
                        response.sendRedirect("/login")
                    )
                    .accessDeniedHandler((request, response, accessDeniedException) ->
                        response.sendRedirect("/access-denied")
                    )
            )
            .build();
    }

    private void defaultSessionConfigurer(SessionManagementConfigurer<HttpSecurity> sessionConfigurer) {
        sessionConfigurer
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            .sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::migrateSession);
    }

    private void defaultCsrfConfigurer(CsrfConfigurer<HttpSecurity> csrfConfigurer) {
        csrfConfigurer
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }

    private AuthenticationFailureHandler authenticationFailureHandler() {
        LinkedHashMap<Class<? extends AuthenticationException>, AuthenticationFailureHandler> handlers = new LinkedHashMap<>(1);

        handlers.put(OAuth2AuthenticationException.class, new OAuth2AuthenticationFailureHandler());

        return new DelegatingAuthenticationFailureHandler(
            handlers,
            new SimpleUrlAuthenticationFailureHandler("/login?auth-error")
        );
    }

    private static class OAuth2AuthenticationFailureHandler implements AuthenticationFailureHandler {

        private final RedirectStrategy redirectStrategy;

        public OAuth2AuthenticationFailureHandler() {
            this.redirectStrategy = new DefaultRedirectStrategy();
        }

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
            OAuth2AuthenticationException oauthException = (OAuth2AuthenticationException) exception;

            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            redirectStrategy.sendRedirect(request, response, String.format("/login?%s", oauthException.getError().getErrorCode()));
        }
    }
}

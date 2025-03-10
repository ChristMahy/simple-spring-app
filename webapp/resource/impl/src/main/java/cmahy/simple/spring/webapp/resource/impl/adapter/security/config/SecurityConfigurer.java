package cmahy.simple.spring.webapp.resource.impl.adapter.security.config;

import cmahy.simple.spring.webapp.resource.impl.adapter.security.oauth2.TacoResourceOAuth2Service;
import cmahy.simple.spring.webapp.resource.impl.adapter.security.oidc.TacoResourceOidcService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.io.IOException;
import java.util.LinkedHashMap;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableMethodSecurity
public class SecurityConfigurer {

    private static final Logger LOG = LoggerFactory.getLogger(SecurityConfigurer.class);

    @Bean
    protected MvcRequestMatcher.Builder mvcMatcherBuilder(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    @Order(1)
    @ConditionalOnExpression(
        """
            ${spring.h2.console.enabled:false} eq true or
            ${spring.h2.console.enabled:false} eq 'true' or
            ${spring.h2.console.enabled:false} eq 'on' or
            ${spring.h2.console.enabled:false} eq '1'
        """
    )
    public SecurityFilterChain h2ConsoleSecurityFilterChain(HttpSecurity http) throws Exception {
        PathRequest.H2ConsoleRequestMatcher h2ConsoleRequestMatcher = PathRequest.toH2Console();

        return http
            .securityMatcher(h2ConsoleRequestMatcher)
            .authorizeHttpRequests(authorized -> authorized.requestMatchers(h2ConsoleRequestMatcher).permitAll())
            .csrf(csrf -> csrf.ignoringRequestMatchers(PathRequest.toH2Console()))
            .headers(headerConfigurer -> headerConfigurer.frameOptions(
                HeadersConfigurer.FrameOptionsConfig::sameOrigin
            ))
            .build();
    }

    @Bean
    @ConditionalOnProperty(name = "spring-app.security.oauth2.enable", havingValue = "false", matchIfMissing = true)
    @Order(100)
    public SecurityFilterChain securityFilterChain(
        HttpSecurity http,
        MvcRequestMatcher.Builder mvcMatcherBuilder
    ) throws Exception {

        LOG.info("Setup normal login configuration");

        return defaultSecurity(mvcMatcherBuilder, http).build();
    }

    @Bean
    @ConditionalOnProperty(name = "spring-app.security.oauth2.enable", havingValue = "true")
    @Order(100)
    public SecurityFilterChain securityFilterChainWithOAuth2(
        HttpSecurity http,
        MvcRequestMatcher.Builder mvcMatcherBuilder,
        TacoResourceOAuth2Service tacoResourceOAuth2Service,
        TacoResourceOidcService tacoResourceOidcService
    ) throws Exception {

        LOG.info("OAuth2 Security Configurer active");

        http.authorizeHttpRequests(registry -> registry.requestMatchers(antMatcher("/oauth2/authorization**")).permitAll());

        return defaultSecurity(mvcMatcherBuilder, http)
            .oauth2Login(configurer -> {
                configurer
                    .loginPage("/login").permitAll()
                    .failureHandler(authenticationFailureHandler())
                    .userInfoEndpoint(userInfoEndpoint -> {
                        userInfoEndpoint.userService(tacoResourceOAuth2Service);
                        userInfoEndpoint.oidcUserService(tacoResourceOidcService);
                    });
            })
            .oauth2Client(Customizer.withDefaults())
            .build();
    }

    private HttpSecurity defaultSecurity(
        MvcRequestMatcher.Builder mvcMatcherBuilder,
        HttpSecurity http
    ) throws Exception {
        return http
            .anonymous(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(registry -> {
                registry
                    .requestMatchers(antMatcher(HttpMethod.OPTIONS)).permitAll()
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                    .requestMatchers(mvcMatcherBuilder.pattern( "")).permitAll()
                    .requestMatchers(mvcMatcherBuilder.pattern( "/")).permitAll()
                    .requestMatchers(mvcMatcherBuilder.pattern( "/without-controller")).permitAll()
                    .requestMatchers(mvcMatcherBuilder.pattern( "/toggle-theme")).permitAll()
                    .requestMatchers(mvcMatcherBuilder.pattern("/register**")).permitAll()
                    .requestMatchers(mvcMatcherBuilder.pattern("/login**")).permitAll()
                    .anyRequest().fullyAuthenticated();
            })
            .csrf(csrfConfigurer -> {
                csrfConfigurer
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                    .ignoringRequestMatchers(antMatcher("/api/**"));
            })
            .cors(Customizer.withDefaults())
            .sessionManagement(sessionConfigurer -> {
                sessionConfigurer
                    // TODO: Trick with stateless session doesn't work, Html thymeleaf requires a session. Explore io.jsonwebtoken ???
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::migrateSession);
            })
            .logout(configurer -> configurer
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
            )
            .formLogin(configurer -> configurer.loginPage("/login"))
            .httpBasic(Customizer.withDefaults());
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
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
            OAuth2AuthenticationException oauthException = (OAuth2AuthenticationException) exception;

            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            redirectStrategy.sendRedirect(request, response, String.format("/login?%s", oauthException.getError().getErrorCode()));
        }
    }
}

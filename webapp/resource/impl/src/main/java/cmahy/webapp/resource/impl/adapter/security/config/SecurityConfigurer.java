package cmahy.webapp.resource.impl.adapter.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static org.springframework.security.config.Customizer.withDefaults;
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
    @Profile("dev")
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
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService
    ) throws Exception {
        LOG.info("OAuth2 Security Configurer active");

        return defaultSecurity(mvcMatcherBuilder, http)
            .oauth2Login(configurer -> {
                configurer
                    .loginPage("/login")
                    .userInfoEndpoint(userInfoEndpoint -> {
                        userInfoEndpoint.userService(oAuth2UserService);
                    });
            })
            .build();
    }

    private HttpSecurity defaultSecurity(MvcRequestMatcher.Builder mvcMatcherBuilder, HttpSecurity http) throws Exception {
        return http
            .authorizeHttpRequests(registry -> {
                registry
                    .requestMatchers(antMatcher(HttpMethod.OPTIONS)).permitAll()
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                    .requestMatchers(mvcMatcherBuilder.pattern("/login**")).permitAll()
                    .anyRequest().authenticated();
            })
            .csrf(withDefaults())
            .sessionManagement(sessionConfigurer -> {
                sessionConfigurer
                    // TODO: Trick with stateless session doesn't work, Html thymeleaf requires a session
//                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::migrateSession);
            })
            // TODO: Trick with stateless session doesn't work
//            .httpBasic(basic -> basic
//                .addObjectPostProcessor(new ObjectPostProcessor<BasicAuthenticationFilter>() {
//                    @Override
//                    public <O extends BasicAuthenticationFilter> O postProcess(O filter) {
//                        filter.setSecurityContextRepository(
//                            new DelegatingSecurityContextRepository(
//                                new RequestAttributeSecurityContextRepository(),
//                                new HttpSessionSecurityContextRepository()
//                            )
//                        );
//                        return filter;
//                    }
//                })
//            )
            .logout(configurer -> configurer.logoutSuccessUrl("/"))
            .formLogin(configurer -> configurer.loginPage("/login"));
    }
}

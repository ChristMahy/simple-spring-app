package cmahy.springapp.resourceserver.config.security;

import cmahy.springapp.resourceserver.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.zalando.problem.jackson.ProblemModule;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

import static org.slf4j.LoggerFactory.getLogger;

@Configuration
@EnableMethodSecurity
@EnableAutoConfiguration(exclude = ErrorMvcAutoConfiguration.class)
@Import(SecurityProblemSupport.class)
public class SecurityConfig {
    private static final Logger LOG = getLogger(SecurityConfig.class);

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

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain securityFilterChain(
        HttpSecurity httpSecurity,
        SecurityProblemSupport problemSupport
    ) throws Exception {
        LOG.info("Setup default security configuration");

        httpSecurity
            .exceptionHandling()
            .authenticationEntryPoint(problemSupport)
            .accessDeniedHandler(problemSupport);

        return httpSecurity.build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().registerModules(
                new ProblemModule(),
                new ConstraintViolationProblemModule());
    }
}

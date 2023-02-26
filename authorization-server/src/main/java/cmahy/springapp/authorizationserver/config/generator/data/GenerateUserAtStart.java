package cmahy.springapp.authorizationserver.config.generator.data;

import cmahy.springapp.authorizationserver.domain.Role;
import cmahy.springapp.authorizationserver.domain.User;
import cmahy.springapp.authorizationserver.repository.RoleRepository;
import cmahy.springapp.authorizationserver.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

import static java.nio.charset.StandardCharsets.UTF_8;

@Configuration
public class GenerateUserAtStart {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public GenerateUserAtStart(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public ApplicationRunner loadUser() {
        return args -> {
            final Role admin = roleRepository.save(new Role(
                null,
                "ADMIN",
                new HashSet<>()
            ));

            final User user = new User(
                null,
                "test",
                passwordEncoder.encode("test").getBytes(UTF_8),
                new HashSet<>()
            )
                .addRole(admin);

            userRepository.save(user);
        };
    }
}

package cmahy.springapp.config;

import cmahy.springapp.domain.User;
import cmahy.springapp.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class GenerateUserAtStart {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public GenerateUserAtStart(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public ApplicationRunner loadUsers() {
        return args -> {
            userRepository.save(new User(
                null,
                "test",
                passwordEncoder.encode("test"),
                "test",
                "test",
                "test",
                "test",
                "test",
                "0499665588"
            ));
        };
    }
}

package cmahy.springapp.resourceserver.config.generator.data;

import cmahy.springapp.resourceserver.domain.Role;
import cmahy.springapp.resourceserver.domain.User;
import cmahy.springapp.resourceserver.repository.RoleRepository;
import cmahy.springapp.resourceserver.repository.UserRepository;
import cmahy.springapp.resourceserver.security.common.AuthProvider;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static cmahy.springapp.resourceserver.config.security.AuthorizationConstant.ROLE_USER;
import static java.util.Collections.emptyList;

@Configuration
public class GenerateUserAtStart implements ApplicationRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public GenerateUserAtStart(
        UserRepository userRepository,
        RoleRepository roleRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var roleNormal = roleRepository.save(new Role(null, ROLE_USER, emptyList()));

        userRepository.save(new User(
            10L,
            "test",
            passwordEncoder.encode("test"),
            "test",
            "test",
            "test",
            "test",
            "test",
            "0499665588",
            AuthProvider.LOCAL,
            false,
            false,
            true,
            false,
            List.of(roleNormal)
        ));
    }
}

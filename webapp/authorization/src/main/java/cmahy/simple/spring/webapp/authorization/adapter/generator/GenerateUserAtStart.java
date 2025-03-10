package cmahy.simple.spring.webapp.authorization.adapter.generator;

import cmahy.simple.spring.webapp.authorization.application.repository.RoleRepository;
import cmahy.simple.spring.webapp.authorization.application.repository.UserRepository;
import cmahy.simple.spring.webapp.authorization.domain.Role;
import cmahy.simple.spring.webapp.authorization.domain.User;
import cmahy.simple.spring.webapp.authorization.exception.RoleNotFoundException;
import jakarta.inject.Named;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Stream;

@Named
public class GenerateUserAtStart implements ApplicationRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public GenerateUserAtStart(
        RoleRepository roleRepository,
        UserRepository userRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public void run(ApplicationArguments args) throws Exception {
        Stream.of("ADMIN", "GUEST")
            .forEach(role -> {
                roleRepository.save(Role.builder()
                    .name(role)
                    .users(new HashSet<>())
                    .build());
            });

        Role admin = roleRepository.findByName("ADMIN")
            .orElseThrow(() -> new RoleNotFoundException(
                "Role <ADMIN> not found"
            ));

        Role guest = roleRepository.findByName("GUEST")
            .orElseThrow(() -> new RoleNotFoundException(
                "Role <GUEST> not found"
            ));

        userRepository.save(
            User.builder()
                .username("admin_spring")
                .password(
                    passwordEncoder
                        .encode("admin_spring")
                        .getBytes(StandardCharsets.UTF_8)
                )
                .build()
                .addRole(admin)
        );

        userRepository.save(
            User.builder()
                .username("guest_spring")
                .password(
                    passwordEncoder
                        .encode("guest_spring")
                        .getBytes(StandardCharsets.UTF_8)
                )
                .build()
                .addRole(guest)
        );
    }
}

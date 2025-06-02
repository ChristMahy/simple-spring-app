package cmahy.simple.spring.webapp.authorization.adapter.generator;

import cmahy.simple.spring.webapp.authorization.application.repository.*;
import cmahy.simple.spring.webapp.authorization.domain.*;
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

    private final RightRepository rightRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public GenerateUserAtStart(
        RightRepository rightRepository,
        RoleRepository roleRepository,
        UserRepository userRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.rightRepository = rightRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public void run(ApplicationArguments args) throws Exception {
        Stream.of("ingredient:read", "ingredient:write", "ingredient:delete")
            .forEach(right -> {
                rightRepository.save(
                    Right.builder()
                        .roles(new HashSet<>())
                        .name(right)
                        .build()
                );
            });

        Stream.of("ingredient:admin", "ingredient:guest")
            .forEach(role -> {
                roleRepository.save(
                    Role.builder()
                        .name(role)
                        .users(new HashSet<>())
                        .build()
                );
            });

        Role ingredientAdmin = roleRepository.findByName("ingredient:admin")
            .orElseThrow(() -> new RoleNotFoundException(
                "Role <ingredient:admin> not found"
            ));

        ingredientAdmin = bindRoleWithRights(ingredientAdmin, new String[] {"ingredient:read", "ingredient:write", "ingredient:delete"});

        Role ingredientGuest = roleRepository.findByName("ingredient:guest")
            .orElseThrow(() -> new RoleNotFoundException(
                "Role <ingredient:guest> not found"
            ));

        ingredientGuest = bindRoleWithRights(ingredientGuest, new String[] {"ingredient:read"});

        userRepository.save(
            User.builder()
                .username("admin_spring")
                .password(
                    passwordEncoder
                        .encode("admin_spring")
                        .getBytes(StandardCharsets.UTF_8)
                )
                .build()
                .addRole(ingredientAdmin)
        );

        userRepository.save(
            User.builder()
                .username("taco-shop-m2m")
                .password(
                    passwordEncoder
                        .encode("taco-shop-m2m")
                        .getBytes(StandardCharsets.UTF_8)
                )
                .build()
                .addRole(ingredientGuest)
        );

        userRepository.save(
            User.builder()
                .username("shell-client-auth-jwt")
                .build()
                .addRole(ingredientAdmin)
                .addRole(ingredientGuest)
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
                .addRole(ingredientGuest)
        );
    }

    private Role bindRoleWithRights(Role role, String[] rightNames) {
        for (String rightName : rightNames) {
            rightRepository
                .findByName(rightName)
                .ifPresent(role::addRight);
        }

        roleRepository.save(role);

        return roleRepository.findByName(role.getName()).orElseThrow();
    }
}

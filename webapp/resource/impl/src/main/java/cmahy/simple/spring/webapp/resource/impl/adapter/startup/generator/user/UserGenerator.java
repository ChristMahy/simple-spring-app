package cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.user;

import cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.GeneratorConstants;
import cmahy.simple.spring.webapp.user.kernel.application.repository.RoleRepository;
import cmahy.simple.spring.webapp.user.kernel.application.repository.UserSecurityRepository;
import cmahy.simple.spring.webapp.user.kernel.domain.*;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.UserSecurityBuilder;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.factory.UserSecurityBuilderFactory;
import cmahy.simple.spring.webapp.user.kernel.exception.RoleNotFoundException;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;

@Component
@Order(GeneratorConstants.UserGeneratorExecutionOrder.USER)
public class UserGenerator implements ApplicationListener<ApplicationStartedEvent> {

    private final UserSecurityRepository<UserSecurity> userSecurityRepository;
    private final RoleRepository<Role> roleRepository;
    private final UserSecurityBuilderFactory<UserSecurity> userSecurityBuilderFactory;
    private final PasswordEncoder passwordEncoder;

    public UserGenerator(
        UserSecurityRepository userSecurityRepository,
        RoleRepository roleRepository,
        UserSecurityBuilderFactory userSecurityBuilderFactory,
        PasswordEncoder passwordEncoder
    ) {
        this.userSecurityRepository = userSecurityRepository;
        this.roleRepository = roleRepository;
        this.userSecurityBuilderFactory = userSecurityBuilderFactory;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationStartedEvent event) {

        try {
            Role guestRole = roleRepository.findByName("Guest")
                .orElseThrow(() -> new RoleNotFoundException("Guest"));

            Role adminRole = roleRepository.findByName("Admin")
                .orElseThrow(() -> new RoleNotFoundException("Admin"));

            Optional<UserSecurity> machine2machine = userSecurityRepository.findByUserNameAndAuthProvider("machine2machine", AuthProvider.LOCAL);

            if (machine2machine.isEmpty()) {
                UserSecurityBuilder<UserSecurity> machine2machineBuilder = userSecurityBuilderFactory.create()
                    .userName("machine2machine")
                    .fullName("machine2machine")
                    .street("Local")
                    .state("Local_Machine")
                    .city("Machine")
                    .zip("1234")
                    .phoneNumber("Call_Me_Maybe")
                    .password(passwordEncoder.encode("machine2machine").getBytes())
                    .roles(new HashSet<>(2) {{
                        add(guestRole);
                        add(adminRole);
                    }})
                    .authProvider(AuthProvider.LOCAL)
                    .enabled(true)
                    .expired(false)
                    .credentialsExpired(false)
                    .locked(false);

                userSecurityRepository.save(machine2machineBuilder.build());
            }

            Optional<UserSecurity> testUser = userSecurityRepository.findByUserNameAndAuthProvider("test", AuthProvider.LOCAL);

            if (testUser.isEmpty()) {
                UserSecurityBuilder<UserSecurity> testUserBuilder = userSecurityBuilderFactory.create()
                    .userName("test")
                    .fullName("test")
                    .street("Local")
                    .state("Local_Test")
                    .city("Test")
                    .zip("1234")
                    .phoneNumber("Call_Me_Maybe")
                    .password(passwordEncoder.encode("test").getBytes())
                    .roles(new HashSet<>(1) {{
                        add(guestRole);
                    }})
                    .authProvider(AuthProvider.LOCAL)
                    .enabled(true)
                    .expired(false)
                    .credentialsExpired(false)
                    .locked(false);

                userSecurityRepository.save(testUserBuilder.build());
            }
        } catch (Exception any) {
            throw new RuntimeException(any);
        }

    }
}

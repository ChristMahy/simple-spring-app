package cmahy.webapp.resource.impl.adapter.startup.generator.user;

import cmahy.webapp.resource.impl.application.user.repository.RoleRepository;
import cmahy.webapp.resource.impl.application.user.repository.UserSecurityRepository;
import cmahy.webapp.resource.impl.domain.user.*;
import cmahy.webapp.resource.impl.exception.user.RoleNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
@Order(2)
public class UserGenerator implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(UserGenerator.class);

    private final UserSecurityRepository userSecurityRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserGenerator(
        UserSecurityRepository userSecurityRepository,
        RoleRepository roleRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.userSecurityRepository = userSecurityRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        UserSecurity userSecurity = new UserSecurity();

        Role guestRole = roleRepository.findByName("Guest")
            .orElseThrow(() -> new RoleNotFoundException("Guest"));

        Role adminRole = roleRepository.findByName("Admin")
            .orElseThrow(() -> new RoleNotFoundException("Admin"));

        userSecurity.setUserName("machine2machine");
        userSecurity.setFullName("machine2machine");
        userSecurity.setStreet("Local");
        userSecurity.setState("Local_Machine");
        userSecurity.setCity("Machine");
        userSecurity.setZip("1234");
        userSecurity.setPhoneNumber("Call_Me_Maybe");
        userSecurity.setPassword(passwordEncoder.encode("machine2machine").getBytes());
        userSecurity.setRoles(new HashSet<>(2) {{
            add(guestRole);
            add(adminRole);
        }});
        userSecurity.setAuthProvider(AuthProvider.LOCAL);
        userSecurity.setEnabled(true);
        userSecurity.setExpired(false);
        userSecurity.setCredentialsExpired(false);
        userSecurity.setLocked(false);

        userSecurityRepository.save(userSecurity);
    }





















}

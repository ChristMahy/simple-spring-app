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
        UserSecurity machine2MachineUser = new UserSecurity();

        Role guestRole = roleRepository.findByName("Guest")
            .orElseThrow(() -> new RoleNotFoundException("Guest"));

        Role adminRole = roleRepository.findByName("Admin")
            .orElseThrow(() -> new RoleNotFoundException("Admin"));

        machine2MachineUser.setUserName("machine2machine");
        machine2MachineUser.setFullName("machine2machine");
        machine2MachineUser.setStreet("Local");
        machine2MachineUser.setState("Local_Machine");
        machine2MachineUser.setCity("Machine");
        machine2MachineUser.setZip("1234");
        machine2MachineUser.setPhoneNumber("Call_Me_Maybe");
        machine2MachineUser.setPassword(passwordEncoder.encode("machine2machine").getBytes());
        machine2MachineUser.setRoles(new HashSet<>(2) {{
            add(guestRole);
            add(adminRole);
        }});
        machine2MachineUser.setAuthProvider(AuthProvider.LOCAL);
        machine2MachineUser.setEnabled(true);
        machine2MachineUser.setExpired(false);
        machine2MachineUser.setCredentialsExpired(false);
        machine2MachineUser.setLocked(false);

        userSecurityRepository.save(machine2MachineUser);

        UserSecurity testUser = new UserSecurity();

        testUser.setUserName("test");
        testUser.setFullName("test");
        testUser.setStreet("Local");
        testUser.setState("Local_Test");
        testUser.setCity("Test");
        testUser.setZip("1234");
        testUser.setPhoneNumber("Call_Me_Maybe");
        testUser.setPassword(passwordEncoder.encode("test").getBytes());
        testUser.setRoles(new HashSet<>(1) {{
            add(guestRole);
        }});
        testUser.setAuthProvider(AuthProvider.LOCAL);
        testUser.setEnabled(true);
        testUser.setExpired(false);
        testUser.setCredentialsExpired(false);
        testUser.setLocked(false);

        userSecurityRepository.save(testUser);
    }
}

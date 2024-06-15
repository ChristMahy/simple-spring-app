package cmahy.webapp.resource.impl.adapter.startup.generator.user;

import cmahy.webapp.resource.impl.application.user.repository.RoleRepository;
import cmahy.webapp.resource.impl.domain.user.Role;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
@Order(1)
public class RoleGenerator implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(RoleGenerator.class);

    private final RoleRepository roleRepository;

    public RoleGenerator(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        Stream.of("Guest", "Admin")
            .forEach(name -> {
                Role role = new Role();

                role.setName(name);

                role = roleRepository.save(role);

                LOG.info("Role saved <{}>", role);
            });
    }
}

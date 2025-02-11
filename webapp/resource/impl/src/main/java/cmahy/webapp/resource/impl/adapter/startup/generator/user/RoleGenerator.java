package cmahy.webapp.resource.impl.adapter.startup.generator.user;

import cmahy.webapp.user.kernel.application.repository.RoleRepository;
import cmahy.webapp.user.kernel.domain.Role;
import cmahy.webapp.user.kernel.domain.builder.factory.RoleBuilderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@Component
@Order(1)
public class RoleGenerator implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(RoleGenerator.class);

    private final RoleRepository<Role> roleRepository;
    private final RoleBuilderFactory<Role> roleBuilderFactory;

    public RoleGenerator(
        RoleRepository roleRepository,
        RoleBuilderFactory roleBuilderFactory
    ) {
        this.roleRepository = roleRepository;
        this.roleBuilderFactory = roleBuilderFactory;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        Stream.of("Guest", "Admin")
            .forEach(name -> {
                if (roleRepository.findByName(name).isEmpty()) {
                    Role role = roleBuilderFactory.create()
                        .name(name)
                        .build();

                    role = roleRepository.save(role);

                    LOG.info("Role saved <{}>", role);
                }
            });
    }
}

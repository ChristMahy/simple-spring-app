package cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.user;

import cmahy.simple.spring.webapp.user.kernel.application.repository.RightRepository;
import cmahy.simple.spring.webapp.user.kernel.application.repository.RoleRepository;
import cmahy.simple.spring.webapp.user.kernel.domain.Right;
import cmahy.simple.spring.webapp.user.kernel.domain.Role;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.factory.RoleBuilderFactory;
import cmahy.simple.spring.webapp.user.kernel.exception.RightNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Component
@Order(GeneratorConstants.ExecutionOrder.ROLE)
public class RoleGenerator implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(RoleGenerator.class);

    private final RightRepository<Right> rightRepository;
    private final RoleRepository<Role> roleRepository;
    private final RoleBuilderFactory<Role> roleBuilderFactory;

    public RoleGenerator(
        RightRepository rightRepository,
        RoleRepository roleRepository,
        RoleBuilderFactory roleBuilderFactory
    ) {
        this.rightRepository = rightRepository;
        this.roleRepository = roleRepository;
        this.roleBuilderFactory = roleBuilderFactory;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

        Right read = rightRepository.findByName("ingredient:read")
            .orElseThrow(() -> new RightNotFoundException("ingredient:read"));

        Right write = rightRepository.findByName("ingredient:write")
            .orElseThrow(() -> new RightNotFoundException("ingredient:write"));

        Right delete = rightRepository.findByName("ingredient:delete")
            .orElseThrow(() -> new RightNotFoundException("ingredient:delete"));

        Optional<Role> guest = roleRepository.findByName("Guest");

        if (guest.isEmpty()) {
            Role role = roleBuilderFactory.create()
                .name("Guest")
                .rights(Set.of(read))
                .build();

            role = roleRepository.save(role);

            LOG.info("Role saved <{}>", role);
        }

        Optional<Role> admin = roleRepository.findByName("Admin");

        if (admin.isEmpty()) {
            Role role = roleBuilderFactory.create()
                .name("Admin")
                .rights(Set.of(read, write, delete))
                .build();

            role = roleRepository.save(role);

            LOG.info("Role saved <{}>", role);
        }

    }

}

package cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.user;

import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.ApplicationProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.start.OnStartProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.start.ResourcesProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.GeneratorConstants;
import cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.user.vo.input.RightGeneratorInputVo;
import cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.user.vo.input.RoleGeneratorInputVo;
import cmahy.simple.spring.webapp.user.kernel.application.repository.RightRepository;
import cmahy.simple.spring.webapp.user.kernel.application.repository.RoleRepository;
import cmahy.simple.spring.webapp.user.kernel.domain.Right;
import cmahy.simple.spring.webapp.user.kernel.domain.Role;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.factory.RoleBuilderFactory;
import cmahy.simple.spring.webapp.user.kernel.exception.RightNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.*;

@Component
@Order(GeneratorConstants.UserGeneratorExecutionOrder.ROLE)
public class RoleGenerator implements ApplicationListener<ApplicationStartedEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(RoleGenerator.class);

    private final RightRepository<Right> rightRepository;
    private final RoleRepository<Role> roleRepository;
    private final RoleBuilderFactory<Role> roleBuilderFactory;
    private final ApplicationProperties applicationProperties;
    private final ObjectMapper objectMapper;

    public RoleGenerator(
        RightRepository rightRepository,
        RoleRepository roleRepository,
        RoleBuilderFactory roleBuilderFactory,
        ApplicationProperties applicationProperties,
        ObjectMapper objectMapper
    ) {
        this.rightRepository = rightRepository;
        this.roleRepository = roleRepository;
        this.roleBuilderFactory = roleBuilderFactory;
        this.applicationProperties = applicationProperties;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationStartedEvent event) {

        try {

            Optional<Resource> rolesResource = Optional.ofNullable(applicationProperties.onStart())
                .map(OnStartProperties::resources)
                .map(ResourcesProperties::roles);

            if (rolesResource.map(Resource::exists).orElse(Boolean.FALSE)) {

                try (InputStream rolesStream = rolesResource.get().getInputStream()) {
                    List<RoleGeneratorInputVo> roles = objectMapper.readValue(rolesStream, new TypeReference<>() {
                    });

                    for (RoleGeneratorInputVo r : roles) {

                        Set<Right> rights = new HashSet<>();

                        for (RightGeneratorInputVo right : r.rights()) {

                            rights.add(
                                rightRepository
                                    .findByName(right.name())
                                    .orElseThrow(() -> new RightNotFoundException(right.name()))
                            );

                        }

                        Optional<Role> found = roleRepository.findByName(r.name());

                        if (found.isEmpty()) {
                            Role role = roleBuilderFactory.create()
                                .name(r.name())
                                .rights(rights)
                                .build();

                            role = roleRepository.save(role);

                            LOG.info("<{}> saved <{}>", role.getClass().getSimpleName(), role);
                        }
                    }
                }

            }

        } catch (Exception any) {
            throw new RuntimeException(any);
        }

    }

}

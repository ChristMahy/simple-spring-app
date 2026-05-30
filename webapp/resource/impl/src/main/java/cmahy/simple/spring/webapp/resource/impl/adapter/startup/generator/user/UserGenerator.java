package cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.user;

import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.ApplicationProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.start.OnStartProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.start.ResourcesProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.GeneratorConstants;
import cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.user.vo.input.RoleGeneratorInputVo;
import cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.user.vo.input.UserGeneratorInputVo;
import cmahy.simple.spring.webapp.user.kernel.application.repository.RoleRepository;
import cmahy.simple.spring.webapp.user.kernel.application.repository.UserRepository;
import cmahy.simple.spring.webapp.user.kernel.domain.Role;
import cmahy.simple.spring.webapp.user.kernel.domain.User;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.factory.UserBuilderFactory;
import cmahy.simple.spring.webapp.user.kernel.exception.RoleNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
@Order(GeneratorConstants.UserGeneratorExecutionOrder.USER)
public class UserGenerator implements ApplicationListener<ApplicationStartedEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(UserGenerator.class);

    private final UserRepository<User> userRepository;
    private final RoleRepository<Role> roleRepository;
    private final UserBuilderFactory<User> userBuilderFactory;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationProperties applicationProperties;
    private final ObjectMapper objectMapper;

    public UserGenerator(
        UserRepository userRepository,
        RoleRepository roleRepository,
        UserBuilderFactory userBuilderFactory,
        PasswordEncoder passwordEncoder,
        ApplicationProperties applicationProperties,
        ObjectMapper objectMapper
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userBuilderFactory = userBuilderFactory;
        this.passwordEncoder = passwordEncoder;
        this.applicationProperties = applicationProperties;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationStartedEvent event) {

        try {

            Optional<Resource> usersResource = Optional.ofNullable(applicationProperties.onStart())
                .map(OnStartProperties::resources)
                .map(ResourcesProperties::users);

            if (usersResource.map(Resource::exists).orElse(Boolean.FALSE)) {

                try (InputStream usersStream = usersResource.get().getInputStream()) {

                    List<UserGeneratorInputVo> users = objectMapper.readValue(usersStream, new TypeReference<>() {
                    });

                    for (UserGeneratorInputVo userInput : users) {

                        Optional<User> userIsFound = userRepository.findByUserName(userInput.userName());

                        if (userIsFound.isEmpty()) {

                            Set<Role> roles = new HashSet<>();

                            for (RoleGeneratorInputVo r : userInput.roles()) {

                                roles.add(
                                    roleRepository.findByName(r.name())
                                        .orElseThrow(() -> new RoleNotFoundException(r.name()))
                                );

                            }

                            User user = userBuilderFactory.create()
                                .userName(userInput.userName())
                                .password(
                                    Optional
                                        .ofNullable(
                                            passwordEncoder.encode(userInput.userName())
                                        )
                                        .orElseGet(() -> UUID.randomUUID().toString())
                                        .getBytes(StandardCharsets.UTF_8)
                                )
                                .fullName(userInput.fullName())
                                .street(userInput.street())
                                .city(userInput.city())
                                .state(userInput.state())
                                .zip(userInput.zip())
                                .phoneNumber(userInput.phoneNumber())
                                .roles(roles)
                                .build();

                            user = userRepository.save(user);

                            LOG.info("<{}> saved <{}>", user.getClass().getSimpleName(), user);

                        }
                    }

                }

            }

        } catch (Exception any) {
            throw new RuntimeException(any);
        }

    }

}

package cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.user;

import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.ApplicationProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.start.OnStartProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.start.ResourcesProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.GeneratorConstants;
import cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.user.vo.input.RoleGeneratorInputVo;
import cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.user.vo.input.UserSecurityGeneratorInputVo;
import cmahy.simple.spring.webapp.user.kernel.application.repository.RoleRepository;
import cmahy.simple.spring.webapp.user.kernel.application.repository.UserSecurityRepository;
import cmahy.simple.spring.webapp.user.kernel.domain.Role;
import cmahy.simple.spring.webapp.user.kernel.domain.UserSecurity;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.factory.UserSecurityBuilderFactory;
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
public class UserSecurityGenerator implements ApplicationListener<ApplicationStartedEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(UserSecurityGenerator.class);

    private final UserSecurityRepository<UserSecurity> userSecurityRepository;
    private final RoleRepository<Role> roleRepository;
    private final UserSecurityBuilderFactory<UserSecurity> userSecurityBuilderFactory;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationProperties applicationProperties;
    private final ObjectMapper objectMapper;

    public UserSecurityGenerator(
        UserSecurityRepository userSecurityRepository,
        RoleRepository roleRepository,
        UserSecurityBuilderFactory userSecurityBuilderFactory,
        PasswordEncoder passwordEncoder,
        ApplicationProperties applicationProperties,
        ObjectMapper objectMapper
    ) {
        this.userSecurityRepository = userSecurityRepository;
        this.roleRepository = roleRepository;
        this.userSecurityBuilderFactory = userSecurityBuilderFactory;
        this.passwordEncoder = passwordEncoder;
        this.applicationProperties = applicationProperties;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationStartedEvent event) {

        try {

            Optional<Resource> usersSecuritiesResource = Optional.ofNullable(applicationProperties.onStart())
                .map(OnStartProperties::resources)
                .map(ResourcesProperties::usersSecurities);

            if (usersSecuritiesResource.map(Resource::exists).orElse(Boolean.FALSE)) {

                try (InputStream usersSecuritiesStream = usersSecuritiesResource.get().getInputStream()) {

                    List<UserSecurityGeneratorInputVo> usersSecurities = objectMapper.readValue(usersSecuritiesStream, new TypeReference<>() {
                    });

                    for (UserSecurityGeneratorInputVo userSecurityInput : usersSecurities) {

                        Optional<UserSecurity> userSecurityIsFound = userSecurityRepository.findByUserNameAndAuthProvider(
                            userSecurityInput.userName(), userSecurityInput.authProvider()
                        );

                        if (userSecurityIsFound.isEmpty()) {

                            Set<Role> roles = new HashSet<>();

                            for (RoleGeneratorInputVo r : userSecurityInput.roles()) {

                                roles.add(
                                    roleRepository.findByName(r.name())
                                        .orElseThrow(() -> new RoleNotFoundException(r.name()))
                                );

                            }

                            UserSecurity userSecurity = userSecurityBuilderFactory.create()
                                .userName(userSecurityInput.userName())
                                .password(
                                    Optional
                                        .ofNullable(
                                            passwordEncoder.encode(userSecurityInput.userName())
                                        )
                                        .orElseGet(() -> UUID.randomUUID().toString())
                                        .getBytes(StandardCharsets.UTF_8)
                                )
                                .fullName(userSecurityInput.fullName())
                                .street(userSecurityInput.street())
                                .city(userSecurityInput.city())
                                .state(userSecurityInput.state())
                                .zip(userSecurityInput.zip())
                                .phoneNumber(userSecurityInput.phoneNumber())
                                .roles(roles)
                                .authProvider(userSecurityInput.authProvider())
                                .expired(userSecurityInput.expired())
                                .locked(userSecurityInput.locked())
                                .enabled(userSecurityInput.enabled())
                                .credentialsExpired(userSecurityInput.credentialsExpired())
                                .build();

                            userSecurity = userSecurityRepository.save(userSecurity);

                            LOG.info("<{}> saved <{}>", userSecurity.getClass().getSimpleName(), userSecurity);

                        }
                    }

                }

            }

        } catch (Exception any) {
            throw new RuntimeException(any);
        }

    }

}

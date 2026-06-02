package cmahy.simple.spring.webapp.resource.integration.test.persistence.application.command;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.resource.integration.test.persistence.application.exception.UnableToGenerateItemException;
import cmahy.simple.spring.webapp.resource.integration.test.persistence.application.repository.*;
import cmahy.simple.spring.webapp.user.kernel.domain.*;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.*;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.factory.*;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Named
public class GenerateRandomUserSecurityWithRoleAndRightCommand {

    private static final Logger LOG = LoggerFactory.getLogger(GenerateRandomUserSecurityWithRoleAndRightCommand.class);

    private final UserSecurityTestRepository<UserSecurity> userSecurityRepository;
    private final RoleTestRepository<Role> roleRepository;
    private final RightTestRepository<Right> rightRepository;
    private final UserSecurityBuilderFactory<UserSecurity> userSecurityBuilderFactory;
    private final RoleBuilderFactory<Role> roleBuilderFactory;
    private final RightBuilderFactory<Right> rightBuilderFactory;
    private final PasswordEncoder passwordEncoder;

    public GenerateRandomUserSecurityWithRoleAndRightCommand(
        UserSecurityTestRepository userSecurityRepository,
        RoleTestRepository roleRepository,
        RightTestRepository rightRepository,
        UserSecurityBuilderFactory userSecurityBuilderFactory,
        RoleBuilderFactory roleBuilderFactory,
        RightBuilderFactory rightBuilderFactory,
        PasswordEncoder passwordEncoder
    ) {
        this.userSecurityRepository = userSecurityRepository;
        this.roleRepository = roleRepository;
        this.rightRepository = rightRepository;
        this.userSecurityBuilderFactory = userSecurityBuilderFactory;
        this.roleBuilderFactory = roleBuilderFactory;
        this.rightBuilderFactory = rightBuilderFactory;
        this.passwordEncoder = passwordEncoder;
    }

    public <U extends UserSecurity> U execute() throws UnableToGenerateItemException {

        return (U) execute(1).stream()
            .findFirst()
            .orElseThrow(() -> new UnableToGenerateItemException(UserSecurity.class));

    }

    public <U extends UserSecurity> Collection<U> execute(int length) throws UnableToGenerateItemException {

        try {

            List<U> users = new ArrayList<>(length);

            RightBuilder<Right> readBuilder = rightBuilderFactory.create();

            Right read = readBuilder
                .name("ingredient:read")
                .build();

            read = rightRepository.save(read);

            RightBuilder<Right> writeBuilder = rightBuilderFactory.create();

            Right write = writeBuilder
                .name("ingredient:write")
                .build();

            write = rightRepository.save(write);

            RightBuilder<Right> deleteBuilder = rightBuilderFactory.create();

            Right delete = deleteBuilder
                .name("ingredient:delete")
                .build();

            delete = rightRepository.save(delete);

            RoleBuilder<Role> roleBuilder = roleBuilderFactory.create();

            Role role = roleBuilder
                .name(Generator.generateAStringWithoutSpecialChars())
                .rights(List.of(read, write, delete))
                .build();

            role = roleRepository.save(role);

            for (int i = 0; i < length; i++) {

                UserSecurityBuilder<UserSecurity> userSecurityBuilder = userSecurityBuilderFactory.create();

                String username = Generator.generateAStringWithoutSpecialChars();
                String password = Generator.generateAStringWithoutSpecialChars();

                LOG.info("Generating user <{}> with password <{}>", username, password);

                UserSecurity user = userSecurityBuilder
                    .roles(Set.of(role))
                    .userName(username)
                    .fullName(Generator.generateAStringWithoutSpecialChars())
                    .password(
                        passwordEncoder.encode(password).getBytes(StandardCharsets.UTF_8)
                    )
                    .street(Generator.generateAString())
                    .city(Generator.generateAString())
                    .state(Generator.generateAString())
                    .zip(String.valueOf(Generator.randomInt(0, 9999)))
                    .phoneNumber(Generator.generateAString())
                    .authProvider(AuthProvider.LOCAL)
                    .expired(false)
                    .locked(false)
                    .enabled(true)
                    .credentialsExpired(false)
                    .build();

                user = userSecurityRepository.save(user);

                users.add((U) user);

            }

            return users;

        } catch (Exception any) {

            throw new UnableToGenerateItemException(UserSecurity.class, any);

        }

    }
}

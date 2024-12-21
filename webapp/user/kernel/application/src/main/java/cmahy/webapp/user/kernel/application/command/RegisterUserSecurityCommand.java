package cmahy.webapp.user.kernel.application.command;

import cmahy.common.annotation.Command;
import cmahy.webapp.user.kernel.application.mapper.input.UserSecurityInputAppVoMapper;
import cmahy.webapp.user.kernel.application.mapper.output.UserSecurityOutputAppVoMapper;
import cmahy.webapp.user.kernel.application.repository.RoleRepository;
import cmahy.webapp.user.kernel.application.repository.UserSecurityRepository;
import cmahy.webapp.user.kernel.domain.Role;
import cmahy.webapp.user.kernel.domain.UserSecurity;
import cmahy.webapp.user.kernel.domain.builder.factory.UserSecurityBuilderFactory;
import cmahy.webapp.user.kernel.exception.RoleNotFoundException;
import cmahy.webapp.user.kernel.exception.UserExistsException;
import cmahy.webapp.user.kernel.vo.input.UserSecurityInputAppVo;
import cmahy.webapp.user.kernel.vo.output.UserSecurityOutputAppVo;
import jakarta.inject.Named;

import java.util.HashSet;
import java.util.Optional;

@Command
@Named
public class RegisterUserSecurityCommand {

    private final UserSecurityRepository<UserSecurity> userSecurityRepository;
    private final RoleRepository<Role> roleRepository;
    private final UserSecurityInputAppVoMapper userSecurityInputAppVoMapper;
    private final UserSecurityOutputAppVoMapper userSecurityOutputAppVoMapper;
    private final UserSecurityBuilderFactory<UserSecurity> userSecurityBuilderFactory;

    public RegisterUserSecurityCommand(
        UserSecurityRepository userSecurityRepository,
        RoleRepository roleRepository,
        UserSecurityInputAppVoMapper userSecurityInputAppVoMapper,
        UserSecurityOutputAppVoMapper userSecurityOutputAppVoMapper,
        UserSecurityBuilderFactory userSecurityBuilderFactory
    ) {
        this.userSecurityRepository = userSecurityRepository;
        this.roleRepository = roleRepository;
        this.userSecurityInputAppVoMapper = userSecurityInputAppVoMapper;
        this.userSecurityOutputAppVoMapper = userSecurityOutputAppVoMapper;
        this.userSecurityBuilderFactory = userSecurityBuilderFactory;
    }

    public UserSecurityOutputAppVo execute(UserSecurityInputAppVo userSecurityInput) throws UserExistsException, RoleNotFoundException {

        Optional<UserSecurity> byUserName = userSecurityRepository.findByUserNameAndAuthProvider(userSecurityInput.userName(), userSecurityInput.authProvider());

        if (byUserName.isPresent()) {
            throw new UserExistsException(String.format(
                "Username <%s> already exists, with provider <%s>",
                byUserName.get().getUserName(),
                byUserName.get().getAuthProvider().name()
            ));
        }

        Role guestRole = roleRepository
            .findByName("Guest")
            .orElseThrow(() -> new RoleNotFoundException("Guest"));

        UserSecurity userSecurity = userSecurityInputAppVoMapper.map(userSecurityInput);

        userSecurity = userSecurityBuilderFactory.create(userSecurity)
            .roles(new HashSet<>(1) {{
                add(guestRole);
            }})
            .build();

        return userSecurityOutputAppVoMapper.map(
            userSecurityRepository.save(userSecurity)
        );
    }
}

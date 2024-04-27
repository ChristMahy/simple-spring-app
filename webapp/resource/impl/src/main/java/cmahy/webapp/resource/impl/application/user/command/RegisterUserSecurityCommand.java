package cmahy.webapp.resource.impl.application.user.command;

import cmahy.common.annotation.Command;
import cmahy.webapp.resource.impl.application.user.mapper.input.UserSecurityInputAppVoMapper;
import cmahy.webapp.resource.impl.application.user.mapper.output.UserSecurityOutputAppVoMapper;
import cmahy.webapp.resource.impl.application.user.repository.RoleRepository;
import cmahy.webapp.resource.impl.application.user.repository.UserSecurityRepository;
import cmahy.webapp.resource.impl.application.user.vo.input.UserSecurityInputAppVo;
import cmahy.webapp.resource.impl.application.user.vo.output.UserSecurityOutputAppVo;
import cmahy.webapp.resource.impl.domain.user.Role;
import cmahy.webapp.resource.impl.domain.user.UserSecurity;
import jakarta.inject.Named;

import java.util.HashSet;

@Command
@Named
public class RegisterUserSecurityCommand {

    private final UserSecurityRepository userSecurityRepository;
    private final RoleRepository roleRepository;
    private final UserSecurityInputAppVoMapper userSecurityInputAppVoMapper;
    private final UserSecurityOutputAppVoMapper userSecurityOutputAppVoMapper;

    public RegisterUserSecurityCommand(
        UserSecurityRepository userSecurityRepository,
        RoleRepository roleRepository,
        UserSecurityInputAppVoMapper userSecurityInputAppVoMapper,
        UserSecurityOutputAppVoMapper userSecurityOutputAppVoMapper
    ) {
        this.userSecurityRepository = userSecurityRepository;
        this.roleRepository = roleRepository;
        this.userSecurityInputAppVoMapper = userSecurityInputAppVoMapper;
        this.userSecurityOutputAppVoMapper = userSecurityOutputAppVoMapper;
    }

    public UserSecurityOutputAppVo execute(UserSecurityInputAppVo userSecurityInput) {
        Role guestRole = roleRepository
            .findByName("Guest")
            .orElseThrow(() -> new RuntimeException("Role <Guest> not found"));

        UserSecurity userSecurity = userSecurityInputAppVoMapper.map(userSecurityInput);

        userSecurity.setRoles(new HashSet<>(1) {{
            add(guestRole);
        }});

        return userSecurityOutputAppVoMapper.map(
            userSecurityRepository.save(userSecurity)
        );
    }
}

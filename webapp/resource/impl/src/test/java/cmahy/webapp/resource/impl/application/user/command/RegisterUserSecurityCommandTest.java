package cmahy.webapp.resource.impl.application.user.command;

import cmahy.webapp.resource.impl.application.user.mapper.input.UserSecurityInputAppVoMapper;
import cmahy.webapp.resource.impl.application.user.mapper.output.UserSecurityOutputAppVoMapper;
import cmahy.webapp.resource.impl.application.user.repository.RoleRepository;
import cmahy.webapp.resource.impl.application.user.repository.UserSecurityRepository;
import cmahy.webapp.resource.impl.application.user.vo.input.UserSecurityInputAppVo;
import cmahy.webapp.resource.impl.application.user.vo.output.UserOutputAppVo;
import cmahy.webapp.resource.impl.application.user.vo.output.UserSecurityOutputAppVo;
import cmahy.webapp.resource.impl.domain.user.Role;
import cmahy.webapp.resource.impl.domain.user.UserSecurity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterUserSecurityCommandTest {

    @Mock
    private UserSecurityRepository userSecurityRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserSecurityInputAppVoMapper userSecurityInputAppVoMapper;

    @Mock
    private UserSecurityOutputAppVoMapper userSecurityOutputAppVoMapper;

    @InjectMocks
    private RegisterUserSecurityCommand registerUserSecurityCommand;

    @Test
    void execute() {
        assertDoesNotThrow(() -> {
            UserSecurityInputAppVo inputAppVo = mock(UserSecurityInputAppVo.class);
            Role role = mock(Role.class);
            UserSecurity userSecurity = mock(UserSecurity.class);
            UserSecurityOutputAppVo outputAppVo = mock(UserSecurityOutputAppVo.class);

            when(roleRepository.findByName(anyString())).thenReturn(Optional.of(role));
            when(userSecurityInputAppVoMapper.map(inputAppVo)).thenReturn(userSecurity);
            when(userSecurityRepository.save(userSecurity)).thenReturn(userSecurity);
            when(userSecurityOutputAppVoMapper.map(userSecurity)).thenReturn(outputAppVo);

            UserSecurityOutputAppVo actual = registerUserSecurityCommand.execute(inputAppVo);

            assertThat(actual)
                .isNotNull()
                .isEqualTo(outputAppVo);
        });
    }

    @Test
    void execute_whenRoleNotFound_thenThrowRoleNotFoundException() {
        assertThrows(RuntimeException.class, () -> {
            UserSecurityInputAppVo inputAppVo = mock(UserSecurityInputAppVo.class);

            when(roleRepository.findByName(anyString())).thenReturn(Optional.empty());

            registerUserSecurityCommand.execute(inputAppVo);
        });
    }
}
package cmahy.webapp.user.kernel.application.command;

import cmahy.common.helper.Generator;
import cmahy.webapp.user.kernel.application.mapper.input.UserSecurityInputAppVoMapper;
import cmahy.webapp.user.kernel.application.mapper.output.UserSecurityOutputAppVoMapper;
import cmahy.webapp.user.kernel.application.repository.RoleRepository;
import cmahy.webapp.user.kernel.application.repository.UserSecurityRepository;
import cmahy.webapp.user.kernel.domain.*;
import cmahy.webapp.user.kernel.domain.builder.UserSecurityBuilder;
import cmahy.webapp.user.kernel.domain.builder.factory.UserSecurityBuilderFactory;
import cmahy.webapp.user.kernel.exception.RoleNotFoundException;
import cmahy.webapp.user.kernel.exception.UserExistsException;
import cmahy.webapp.user.kernel.vo.input.UserSecurityInputAppVo;
import cmahy.webapp.user.kernel.vo.output.UserSecurityOutputAppVo;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterUserSecurityCommandTest {

    @Mock
    private UserSecurityRepository<UserSecurity> userSecurityRepository;

    @Mock
    private RoleRepository<Role> roleRepository;

    @Mock
    private UserSecurityInputAppVoMapper userSecurityInputAppVoMapper;

    @Mock
    private UserSecurityOutputAppVoMapper userSecurityOutputAppVoMapper;

    @Mock
    private UserSecurityBuilderFactory<UserSecurity> userSecurityBuilderFactory;

    @InjectMocks
    private RegisterUserSecurityCommand registerUserSecurityCommand;

    @Test
    void execute() {
        assertDoesNotThrow(() -> {
            String username = Generator.generateAString();

            UserSecurityInputAppVo inputAppVo = mock(UserSecurityInputAppVo.class);
            AuthProvider authProvider = mock(AuthProvider.class);
            Role role = mock(Role.class);
            UserSecurity userSecurity = mock(UserSecurity.class);
            UserSecurityOutputAppVo outputAppVo = mock(UserSecurityOutputAppVo.class);
            UserSecurityBuilder<UserSecurity> builder = mock(UserSecurityBuilder.class, RETURNS_SELF);

            when(inputAppVo.userName()).thenReturn(username);
            when(inputAppVo.authProvider()).thenReturn(authProvider);

            when(builder.build()).thenReturn(userSecurity);

            when(userSecurityRepository.findByUserNameAndAuthProvider(username, authProvider)).thenReturn(Optional.empty());
            when(roleRepository.findByName(anyString())).thenReturn(Optional.of(role));
            when(userSecurityInputAppVoMapper.map(inputAppVo)).thenReturn(userSecurity);
            when(userSecurityRepository.save(userSecurity)).thenReturn(userSecurity);
            when(userSecurityOutputAppVoMapper.map(userSecurity)).thenReturn(outputAppVo);
            when(userSecurityBuilderFactory.create(userSecurity)).thenReturn(builder);

            UserSecurityOutputAppVo actual = registerUserSecurityCommand.execute(inputAppVo);

            assertThat(actual)
                .isNotNull()
                .isEqualTo(outputAppVo);
        });
    }

    @Test
    void execute_whenRoleNotFound_thenThrowRoleNotFoundException() {
        assertThrows(RoleNotFoundException.class, () -> {
            String username = Generator.generateAString();
            AuthProvider authProvider = mock(AuthProvider.class);

            UserSecurityInputAppVo inputAppVo = mock(UserSecurityInputAppVo.class);

            when(inputAppVo.userName()).thenReturn(username);
            when(inputAppVo.authProvider()).thenReturn(authProvider);

            when(userSecurityRepository.findByUserNameAndAuthProvider(username, authProvider)).thenReturn(Optional.empty());
            when(roleRepository.findByName(anyString())).thenReturn(Optional.empty());

            registerUserSecurityCommand.execute(inputAppVo);
        });
    }

    @Test
    void execute_whenUserAlreadyExistsWithUserName_thenThrowException() {
        assertThrows(UserExistsException.class, () -> {
            String username = Generator.generateAString();
            AuthProvider authProvider = mock(AuthProvider.class);

            UserSecurityInputAppVo inputAppVo = mock(UserSecurityInputAppVo.class);
            UserSecurity userSecurity = mock(UserSecurity.class, RETURNS_DEEP_STUBS);

            when(userSecurity.getUserName()).thenReturn(Generator.generateAString());
            when(userSecurity.getAuthProvider().name()).thenReturn(Generator.generateAString());

            when(inputAppVo.userName()).thenReturn(username);
            when(inputAppVo.authProvider()).thenReturn(authProvider);

            when(userSecurityRepository.findByUserNameAndAuthProvider(username, authProvider)).thenReturn(Optional.of(userSecurity));

            registerUserSecurityCommand.execute(inputAppVo);
        });
    }
}
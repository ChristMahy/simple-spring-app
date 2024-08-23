package cmahy.webapp.user.kernel.application.query;

import cmahy.common.helper.Generator;
import cmahy.webapp.user.kernel.application.mapper.output.UserSecurityOutputAppVoMapper;
import cmahy.webapp.user.kernel.application.repository.UserSecurityRepository;
import cmahy.webapp.user.kernel.domain.AuthProvider;
import cmahy.webapp.user.kernel.domain.UserSecurity;
import cmahy.webapp.user.kernel.exception.UserNotFoundException;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUserSecurityByUsernameQueryTest {

    @Mock
    private UserSecurityRepository userSecurityRepository;

    @Mock
    private UserSecurityOutputAppVoMapper userSecurityOutputAppVoMapper;

    @InjectMocks
    private GetUserSecurityByUsernameQuery getUserSecurityByUsernameQuery;

    @Test
    void execute() {
        assertDoesNotThrow(() -> {
            String username = Generator.generateAString();
            AuthProvider authProvider = Generator.randomEnum(AuthProvider.class);
            UserSecurity userSecurity = mock(UserSecurity.class);
            UserSecurityOutputAppVo userSecurityOutputAppVo = mock(UserSecurityOutputAppVo.class);

            when(userSecurityRepository.findByUserNameAndAuthProvider(username, authProvider)).thenReturn(Optional.of(userSecurity));
            when(userSecurityOutputAppVoMapper.map(userSecurity)).thenReturn(userSecurityOutputAppVo);

            UserSecurityOutputAppVo actual = getUserSecurityByUsernameQuery.execute(username, authProvider);

            assertThat(actual)
                .isNotNull()
                .isEqualTo(userSecurityOutputAppVo);
        });
    }

    @Test
    void execute_whenUsernameNotFound_thenThrowUserNotFoundException() {
        assertThrows(UserNotFoundException.class, () -> {
            String username = Generator.generateAString();
            AuthProvider authProvider = Generator.randomEnum(AuthProvider.class);

            when(userSecurityRepository.findByUserNameAndAuthProvider(username, authProvider)).thenReturn(Optional.empty());

            getUserSecurityByUsernameQuery.execute(username, authProvider);
        });
    }
}
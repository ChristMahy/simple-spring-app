package cmahy.webapp.resource.impl.application.user.query;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.application.user.mapper.output.UserSecurityOutputAppVoMapper;
import cmahy.webapp.resource.impl.application.user.repository.UserSecurityRepository;
import cmahy.webapp.resource.impl.application.user.vo.output.UserSecurityOutputAppVo;
import cmahy.webapp.resource.impl.domain.user.UserSecurity;
import cmahy.webapp.resource.impl.exception.user.UserNotFoundException;
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
            UserSecurity userSecurity = mock(UserSecurity.class);
            UserSecurityOutputAppVo userSecurityOutputAppVo = mock(UserSecurityOutputAppVo.class);

            when(userSecurityRepository.findByUserName(username)).thenReturn(Optional.of(userSecurity));
            when(userSecurityOutputAppVoMapper.map(userSecurity)).thenReturn(userSecurityOutputAppVo);

            UserSecurityOutputAppVo actual = getUserSecurityByUsernameQuery.execute(username);

            assertThat(actual)
                .isNotNull()
                .isEqualTo(userSecurityOutputAppVo);
        });
    }

    @Test
    void execute_whenUsernameNotFound_thenThrowUserNotFoundException() {
        assertThrows(UserNotFoundException.class, () -> {
            String username = Generator.generateAString();

            when(userSecurityRepository.findByUserName(username)).thenReturn(Optional.empty());

            getUserSecurityByUsernameQuery.execute(username);
        });
    }
}
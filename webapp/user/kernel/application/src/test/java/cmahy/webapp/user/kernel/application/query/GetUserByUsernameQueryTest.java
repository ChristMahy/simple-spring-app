package cmahy.webapp.user.kernel.application.query;

import cmahy.common.helper.Generator;
import cmahy.webapp.user.kernel.application.mapper.output.UserOutputAppVoMapper;
import cmahy.webapp.user.kernel.application.repository.UserRepository;
import cmahy.webapp.user.kernel.domain.User;
import cmahy.webapp.user.kernel.exception.UserNotFoundException;
import cmahy.webapp.user.kernel.vo.output.UserOutputAppVo;
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
class GetUserByUsernameQueryTest {

    @Mock
    private UserOutputAppVoMapper outputVoMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUserByUsernameQuery getUserByUsernameQuery;

    @Test
    void execute() {
        assertDoesNotThrow(() -> {
            String username = Generator.generateAString();
            User user = mock(User.class);
            UserOutputAppVo userOutputAppVo = mock(UserOutputAppVo.class);

            when(userRepository.findByUserName(username)).thenReturn(Optional.of(user));
            when(outputVoMapper.map(user)).thenReturn(userOutputAppVo);

            UserOutputAppVo actual = getUserByUsernameQuery.execute(username);

            assertThat(actual)
                .isNotNull()
                .isEqualTo(userOutputAppVo);
        });
    }

    @Test
    void execute_whenUsernameNotFound_thenThrowUserNotFoundException() {
        assertThrows(UserNotFoundException.class, () -> {
            String username = Generator.generateAString();

            when(userRepository.findByUserName(username)).thenReturn(Optional.empty());

            getUserByUsernameQuery.execute(username);
        });
    }
}
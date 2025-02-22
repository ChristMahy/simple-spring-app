package cmahy.simple.spring.webapp.resource.impl.adapter.security.service;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.resource.impl.adapter.security.mapper.output.UserSecurityDetailsMapper;
import cmahy.simple.spring.webapp.resource.ui.vo.output.UserSecurityDetails;
import cmahy.simple.spring.webapp.user.kernel.application.query.GetUserSecurityByUsernameQuery;
import cmahy.simple.spring.webapp.user.kernel.domain.AuthProvider;
import cmahy.simple.spring.webapp.user.kernel.exception.UserNotFoundException;
import cmahy.simple.spring.webapp.user.kernel.vo.output.UserSecurityOutputAppVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private GetUserSecurityByUsernameQuery getUserSecurityByUsernameQuery;

    @Mock
    private UserSecurityDetailsMapper userSecurityDetailsMapper;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Test
    void loadUserByUsername() {
        assertDoesNotThrow(() -> {
            String username = Generator.generateAString();

            UserSecurityOutputAppVo userSecurityOutputAppVo = mock(UserSecurityOutputAppVo.class);
            UserSecurityDetails userDetails = mock(UserSecurityDetails.class);

            when(getUserSecurityByUsernameQuery.execute(username, AuthProvider.LOCAL)).thenReturn(userSecurityOutputAppVo);

            when(userSecurityDetailsMapper.map(userSecurityOutputAppVo)).thenReturn(userDetails);

            UserDetails actual = userDetailsServiceImpl.loadUserByUsername(username);

            assertThat(actual)
                .isNotNull()
                .isEqualTo(userDetails);
        });
    }

    @Test
    void loadUserByUsername_whenNotFound_thenThrowUsernameNotFoundException() {
        assertThrows(UsernameNotFoundException.class, () -> {
            String username = Generator.generateAString();

            when(getUserSecurityByUsernameQuery.execute(username, AuthProvider.LOCAL)).thenThrow(UserNotFoundException.class);

            userDetailsServiceImpl.loadUserByUsername(username);
        });
    }
}
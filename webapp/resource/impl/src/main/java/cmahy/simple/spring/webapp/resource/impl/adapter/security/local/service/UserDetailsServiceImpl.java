package cmahy.simple.spring.webapp.resource.impl.adapter.security.local.service;

import cmahy.simple.spring.webapp.resource.impl.adapter.security.local.mapper.input.UserSecurityDetailsMapper;
import cmahy.simple.spring.webapp.user.kernel.application.query.GetUserSecurityByUsernameQuery;
import cmahy.simple.spring.webapp.user.kernel.domain.AuthProvider;
import cmahy.simple.spring.webapp.user.kernel.exception.UserNotFoundException;
import cmahy.simple.spring.webapp.user.kernel.vo.output.UserSecurityOutputAppVo;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final GetUserSecurityByUsernameQuery getUserSecurityByUsernameQuery;
    private final UserSecurityDetailsMapper userSecurityDetailsMapper;

    public UserDetailsServiceImpl(
        GetUserSecurityByUsernameQuery getUserSecurityByUsernameQuery,
        UserSecurityDetailsMapper userSecurityDetailsMapper
    ) {
        this.getUserSecurityByUsernameQuery = getUserSecurityByUsernameQuery;
        this.userSecurityDetailsMapper = userSecurityDetailsMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UserSecurityOutputAppVo userSecurityOutputAppVo = getUserSecurityByUsernameQuery.execute(username, AuthProvider.LOCAL);

            return userSecurityDetailsMapper.map(userSecurityOutputAppVo);
        } catch (UserNotFoundException userNotFoundException) {
            throw new UsernameNotFoundException(username);
        }
    }
}

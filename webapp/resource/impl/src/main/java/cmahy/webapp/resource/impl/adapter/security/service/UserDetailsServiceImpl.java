package cmahy.webapp.resource.impl.adapter.security.service;

import cmahy.webapp.resource.impl.adapter.security.mapper.output.UserSecurityDetailsMapper;
import cmahy.webapp.resource.impl.application.user.query.GetUserSecurityByUsernameQuery;
import cmahy.webapp.resource.impl.application.user.vo.output.UserSecurityOutputAppVo;
import cmahy.webapp.resource.impl.domain.user.AuthProvider;
import cmahy.webapp.resource.impl.exception.user.UserNotFoundException;
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

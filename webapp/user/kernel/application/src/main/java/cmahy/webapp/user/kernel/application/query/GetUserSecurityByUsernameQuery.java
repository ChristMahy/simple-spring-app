package cmahy.simple.spring.webapp.user.kernel.application.query;

import cmahy.simple.spring.common.annotation.Query;
import cmahy.simple.spring.webapp.user.kernel.application.mapper.output.UserSecurityOutputAppVoMapper;
import cmahy.simple.spring.webapp.user.kernel.application.repository.UserSecurityRepository;
import cmahy.simple.spring.webapp.user.kernel.domain.*;
import cmahy.simple.spring.webapp.user.kernel.exception.UserNotFoundException;
import cmahy.simple.spring.webapp.user.kernel.vo.output.UserSecurityOutputAppVo;
import jakarta.inject.Named;

@Query
@Named
public class GetUserSecurityByUsernameQuery {

    private final UserSecurityRepository<UserSecurity> userSecurityRepository;
    private final UserSecurityOutputAppVoMapper userSecurityOutputAppVoMapper;

    public GetUserSecurityByUsernameQuery(
        UserSecurityRepository userSecurityRepository,
        UserSecurityOutputAppVoMapper userSecurityOutputAppVoMapper
    ) {
        this.userSecurityRepository = userSecurityRepository;
        this.userSecurityOutputAppVoMapper = userSecurityOutputAppVoMapper;
    }

    public UserSecurityOutputAppVo execute(String username, AuthProvider authProvider) throws UserNotFoundException {
        return userSecurityOutputAppVoMapper.map(
            userSecurityRepository.findByUserNameAndAuthProvider(username, authProvider)
                .orElseThrow(() -> new UserNotFoundException(username))
        );
    }
}

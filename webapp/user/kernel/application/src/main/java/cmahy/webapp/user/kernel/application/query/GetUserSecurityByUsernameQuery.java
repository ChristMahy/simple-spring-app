package cmahy.webapp.user.kernel.application.query;

import cmahy.common.annotation.Query;
import cmahy.webapp.user.kernel.application.mapper.output.UserSecurityOutputAppVoMapper;
import cmahy.webapp.user.kernel.application.repository.UserSecurityRepository;
import cmahy.webapp.user.kernel.domain.*;
import cmahy.webapp.user.kernel.exception.UserNotFoundException;
import cmahy.webapp.user.kernel.vo.output.UserSecurityOutputAppVo;
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

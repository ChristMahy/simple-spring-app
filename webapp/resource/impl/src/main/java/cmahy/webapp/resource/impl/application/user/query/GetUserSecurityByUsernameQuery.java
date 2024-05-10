package cmahy.webapp.resource.impl.application.user.query;

import cmahy.common.annotation.Query;
import cmahy.webapp.resource.impl.application.user.mapper.output.UserSecurityOutputAppVoMapper;
import cmahy.webapp.resource.impl.application.user.repository.UserSecurityRepository;
import cmahy.webapp.resource.impl.application.user.vo.output.UserSecurityOutputAppVo;
import cmahy.webapp.resource.impl.domain.user.AuthProvider;
import cmahy.webapp.resource.impl.exception.user.UserNotFoundException;
import jakarta.inject.Named;

@Query
@Named
public class GetUserSecurityByUsernameQuery {

    private final UserSecurityRepository userSecurityRepository;
    private final UserSecurityOutputAppVoMapper userSecurityOutputAppVoMapper;

    public GetUserSecurityByUsernameQuery(
        UserSecurityRepository userSecurityRepository,
        UserSecurityOutputAppVoMapper userSecurityOutputAppVoMapper
    ) {
        this.userSecurityRepository = userSecurityRepository;
        this.userSecurityOutputAppVoMapper = userSecurityOutputAppVoMapper;
    }

    public UserSecurityOutputAppVo execute(String username, AuthProvider authProvider) {
        return userSecurityOutputAppVoMapper.map(
            userSecurityRepository.findByUserNameAndAuthProvider(username, authProvider)
                .orElseThrow(() -> new UserNotFoundException(username))
        );
    }
}

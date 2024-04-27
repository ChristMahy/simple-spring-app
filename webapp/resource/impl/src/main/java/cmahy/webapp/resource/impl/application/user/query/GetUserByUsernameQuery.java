package cmahy.webapp.resource.impl.application.user.query;

import cmahy.common.annotation.Query;
import cmahy.webapp.resource.impl.application.user.mapper.output.UserOutputAppVoMapper;
import cmahy.webapp.resource.impl.application.user.repository.UserRepository;
import cmahy.webapp.resource.impl.application.user.vo.output.UserOutputAppVo;
import cmahy.webapp.resource.impl.domain.user.User;
import cmahy.webapp.resource.impl.exception.user.UserNotFoundException;
import jakarta.inject.Named;

import java.util.Optional;

@Query
@Named
public class GetUserByUsernameQuery {

    private final UserOutputAppVoMapper outputVoMapper;
    private final UserRepository userRepository;

    public GetUserByUsernameQuery(
        UserOutputAppVoMapper outputVoMapper,
        UserRepository userRepository
    ) {
        this.outputVoMapper = outputVoMapper;
        this.userRepository = userRepository;
    }

    public UserOutputAppVo execute(String username) {
        Optional<User> user = userRepository.findByUserName(username);

        if (user.isEmpty()) {
            throw new UserNotFoundException(username);
        }

        return outputVoMapper.map(user.get());
    }
}

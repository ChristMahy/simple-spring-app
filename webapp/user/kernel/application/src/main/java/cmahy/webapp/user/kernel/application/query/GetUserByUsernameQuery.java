package cmahy.webapp.user.kernel.application.query;

import cmahy.common.annotation.Query;
import cmahy.webapp.user.kernel.application.mapper.output.UserOutputAppVoMapper;
import cmahy.webapp.user.kernel.application.repository.UserRepository;
import cmahy.webapp.user.kernel.domain.User;
import cmahy.webapp.user.kernel.exception.UserNotFoundException;
import cmahy.webapp.user.kernel.vo.output.UserOutputAppVo;
import jakarta.inject.Named;

import java.util.Optional;

@Query
@Named
public class GetUserByUsernameQuery {

    private final UserOutputAppVoMapper outputVoMapper;
    private final UserRepository<User> userRepository;

    public GetUserByUsernameQuery(
        UserOutputAppVoMapper outputVoMapper,
        UserRepository userRepository
    ) {
        this.outputVoMapper = outputVoMapper;
        this.userRepository = userRepository;
    }

    public UserOutputAppVo execute(String username) throws UserNotFoundException {
        Optional<User> user = userRepository.findByUserName(username);

        if (user.isEmpty()) {
            throw new UserNotFoundException(username);
        }

        return outputVoMapper.map(user.get());
    }
}

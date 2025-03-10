package cmahy.simple.spring.webapp.authorization.application.query;

import cmahy.simple.spring.common.annotation.Query;
import cmahy.simple.spring.webapp.authorization.application.mapper.output.UserOutputAppMapper;
import cmahy.simple.spring.webapp.authorization.application.repository.UserRepository;
import cmahy.simple.spring.webapp.authorization.application.vo.output.UserOutputAppVo;
import cmahy.simple.spring.webapp.authorization.exception.UserNotFoundException;
import jakarta.inject.Named;
import org.springframework.transaction.annotation.Transactional;

@Query
@Named
public class GetUserByUsernameQuery {

    private final UserRepository userRepository;
    private final UserOutputAppMapper userOutputAppMapper;

    public GetUserByUsernameQuery(
        UserRepository userRepository,
        UserOutputAppMapper userOutputAppMapper
    ) {
        this.userRepository = userRepository;
        this.userOutputAppMapper = userOutputAppMapper;
    }

    @Transactional(readOnly = true)
    public UserOutputAppVo execute(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username)
            .map(userOutputAppMapper::map)
            .orElseThrow(() -> new UserNotFoundException(
                "User <" + username + "> not found"
            ));
    }
}

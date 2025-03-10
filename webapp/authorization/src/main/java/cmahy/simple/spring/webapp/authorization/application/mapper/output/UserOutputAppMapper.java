package cmahy.simple.spring.webapp.authorization.application.mapper.output;

import cmahy.simple.spring.webapp.authorization.application.vo.output.UserOutputAppVo;
import cmahy.simple.spring.webapp.authorization.domain.User;
import cmahy.simple.spring.webapp.authorization.domain.id.UserId;
import jakarta.inject.Named;

import java.util.stream.Collectors;

@Named
public class UserOutputAppMapper {

    private final RoleOutputAppMapper roleOutputAppMapper;

    public UserOutputAppMapper(RoleOutputAppMapper roleOutputAppMapper) {
        this.roleOutputAppMapper = roleOutputAppMapper;
    }

    public UserOutputAppVo map(User user) {
        return new UserOutputAppVo(
            new UserId(user.getId()),
            user.getUsername(),
            user.getPassword(),
            user
                .getRoles().stream()
                .map(roleOutputAppMapper::map)
                .collect(Collectors.toSet())
        );
    }
}

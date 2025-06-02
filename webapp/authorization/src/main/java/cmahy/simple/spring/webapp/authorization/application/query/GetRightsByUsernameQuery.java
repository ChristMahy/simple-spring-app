package cmahy.simple.spring.webapp.authorization.application.query;

import cmahy.simple.spring.common.annotation.Query;
import cmahy.simple.spring.webapp.authorization.application.repository.UserRepository;
import cmahy.simple.spring.webapp.authorization.domain.*;
import jakarta.inject.Named;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Query
@Named
public class GetRightsByUsernameQuery {

    private final UserRepository userRepository;

    public GetRightsByUsernameQuery(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public Set<String> execute(String username) {
        return userRepository.findByUsername(username)
            .map(User::getRoles)
            .map(roles -> roles.stream()
                .flatMap(role -> role
                    .getRights().stream()
                    .map(Right::getName)
                )
                .collect(Collectors.toUnmodifiableSet())
            )
            .orElse(Collections.emptySet());
    }
}

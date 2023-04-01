package cmahy.springapp.resourceserver.service;

import cmahy.springapp.resourceserver.domain.User;
import cmahy.springapp.resourceserver.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetUserByUsernameService {
    private final UserRepository userRepository;

    public GetUserByUsernameService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public User execute(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(
                "User '" + username + "' not found"
            ));
    }
}

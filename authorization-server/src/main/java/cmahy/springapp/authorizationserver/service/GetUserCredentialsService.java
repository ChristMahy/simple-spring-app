package cmahy.springapp.authorizationserver.service;

import cmahy.springapp.authorizationserver.config.UserSecurityDetails;
import cmahy.springapp.authorizationserver.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetUserCredentialsService {
    private final UserRepository userRepository;

    public GetUserCredentialsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public UserSecurityDetails execute(String username) {
        return userRepository.findByUsername(username)
            .map(UserSecurityDetails::new)
            .orElseThrow(() -> new UsernameNotFoundException(
                "User '" + username + "' not found"
            ));
    }
}

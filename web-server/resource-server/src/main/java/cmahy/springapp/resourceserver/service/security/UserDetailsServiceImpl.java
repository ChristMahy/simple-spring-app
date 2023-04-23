package cmahy.springapp.resourceserver.service.security;

import cmahy.springapp.resourceserver.config.security.UserSecurityDetails;
import cmahy.springapp.resourceserver.domain.User;
import cmahy.springapp.resourceserver.repository.UserRepository;
import cmahy.springapp.resourceserver.security.common.AuthProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> { throw new UsernameNotFoundException("Username " + username + " not found !"); });

        if (!AuthProvider.LOCAL.equals(user.getAuthProvider())) {
            throw new IllegalStateException(
                "Username: " + username + " has logged in from " + user.getAuthProvider() + ". Please login with this provider."
            );
        }

        return new UserSecurityDetails(user);
    }
}

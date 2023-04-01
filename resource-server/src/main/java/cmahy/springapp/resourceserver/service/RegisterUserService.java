package cmahy.springapp.resourceserver.service;

import cmahy.springapp.resourceserver.domain.Role;
import cmahy.springapp.resourceserver.domain.User;
import cmahy.springapp.resourceserver.repository.RoleRepository;
import cmahy.springapp.resourceserver.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cmahy.springapp.resourceserver.config.security.AuthorizationConstant.ROLE_USER;

@Service
public class RegisterUserService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public RegisterUserService(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public User execute(User user) {
        Role simpleUserRole = roleRepository
            .findByName(ROLE_USER)
            .orElseThrow(() -> new RuntimeException("Role " + ROLE_USER + " not found"));

        user.setRoles(List.of(simpleUserRole));

        return userRepository.save(user);
    }
}

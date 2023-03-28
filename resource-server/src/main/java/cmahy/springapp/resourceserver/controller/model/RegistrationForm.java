package cmahy.springapp.resourceserver.controller.model;

import cmahy.springapp.resourceserver.domain.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Data
public class RegistrationForm {
    private String username;
    private String password;
    private String fullName;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phone;

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(
            null,
            username,
            passwordEncoder.encode(password),
            fullName,
            street,
            city,
            state,
            zip,
            phone,
            Set.of()
        );
    }
}

package cmahy.springapp.resourceserver.security.oauth2.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class RegisterUser {
    private String email;
    @ToString.Exclude
    private String password;
    @ToString.Exclude
    private String repassword;
    private String firstName;
    private String lastName;
}

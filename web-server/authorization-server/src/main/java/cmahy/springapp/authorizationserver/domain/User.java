package cmahy.springapp.authorizationserver.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "user_app")
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    @ToString.Exclude
    private byte[] password;

    @ToString.Exclude
    @ManyToMany
    private Set<Role> roles;

    public User addRole(Role role) {
        this.roles.add(role);
        role.getUsers().add(this);

        return this;
    }
}
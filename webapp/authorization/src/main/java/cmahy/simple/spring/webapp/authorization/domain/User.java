package cmahy.simple.spring.webapp.authorization.domain;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_app")
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    private byte[] password;

    @ManyToMany
    private Set<Role> roles;

    public User addRole(Role role) {
        if (this.roles == null) {
            this.roles = new HashSet<>();
        }

        this.roles.add(role);
        role.getUsers().add(this);

        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("id", getId())
            .append("username", getUsername())
            .toString();
    }
}

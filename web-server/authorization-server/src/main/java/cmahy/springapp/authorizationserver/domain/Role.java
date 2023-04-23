package cmahy.springapp.authorizationserver.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "user_role")
@NoArgsConstructor(access = PROTECTED, force = true)
@AllArgsConstructor
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ToString.Exclude
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}

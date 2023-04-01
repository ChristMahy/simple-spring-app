package cmahy.springapp.resourceserver.domain;

import cmahy.springapp.resourceserver.security.common.AuthProvider;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Entity
@Table(name = "user_app")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    @ToString.Exclude
    private String password;
    private String fullName;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    @Column(columnDefinition = "smallint default 0")
    private Boolean isExpired;
    @Column(columnDefinition = "smallint default 0")
    private Boolean isLocked;
    @Column(columnDefinition = "smallint default 1")
    private Boolean isEnabled;
    @Column(columnDefinition = "smallint default 0")
    private Boolean isCredentialsExpired = false;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_app_role",
        joinColumns = {
            @JoinColumn(name = "user_app_id")
        },
        inverseJoinColumns = {
            @JoinColumn(name = "role_id")
        },
        uniqueConstraints = {
            @UniqueConstraint(
                name = "u_user_app_role",
                columnNames = {"user_app_id", "role_id"}
            )
        }
    )
    private Collection<Role> roles;
}

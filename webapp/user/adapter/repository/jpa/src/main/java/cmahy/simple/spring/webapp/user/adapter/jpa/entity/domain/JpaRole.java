package cmahy.simple.spring.webapp.user.adapter.jpa.entity.domain;

import cmahy.simple.spring.webapp.user.kernel.domain.Role;
import jakarta.persistence.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "role_app")
public class JpaRole implements Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;
    protected String name;

    @ManyToMany
    protected Collection<JpaUser> users;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "role_right_app",
        joinColumns = @JoinColumn(name = "role_app_id"),
        inverseJoinColumns = @JoinColumn(name = "right_app_id"),
        uniqueConstraints = @UniqueConstraint(
            name = "u_role_right_app",
            columnNames = { "role_app_id", "right_app_id" }
        )
    )
    protected Collection<JpaRight> rights;

    @Override
    public UUID getId() {
        return id;
    }

    public JpaRole setId(UUID id) {
        this.id = id;

        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    public JpaRole setName(String name) {
        this.name = name;

        return this;
    }

    @Override
    public Collection<JpaUser> getUsers() {
        return users;
    }

    public JpaRole setUsers(Collection<JpaUser> users) {
        this.users = users;

        return this;
    }

    @Override
    public Collection<JpaRight> getRights() {
        return rights;
    }

    public JpaRole setRights(Collection<JpaRight> rights) {
        this.rights = rights;

        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("id", getId())
            .append("name", getName())
            .toString();
    }
}

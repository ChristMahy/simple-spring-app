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
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("id", getId())
            .append("name", getName())
            .toString();
    }
}

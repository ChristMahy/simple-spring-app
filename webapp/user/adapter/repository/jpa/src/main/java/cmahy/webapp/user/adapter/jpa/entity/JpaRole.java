package cmahy.webapp.user.adapter.jpa.entity;

import cmahy.webapp.user.kernel.domain.Role;
import jakarta.persistence.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Collection;

@Entity
@Table(name = "role_app")
public class JpaRole implements Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String name;

    @ManyToMany
    protected Collection<JpaUser> users;

    @Override
    public Long getId() {
        return id;
    }

    public JpaRole setId(Long id) {
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

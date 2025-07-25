package cmahy.simple.spring.webapp.user.adapter.jpa.entity.domain;

import cmahy.simple.spring.webapp.user.kernel.domain.Right;
import jakarta.persistence.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "right_app")
public class JpaRight implements Right {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;
    protected String name;

    @ManyToMany(mappedBy = "rights")
    protected Collection<JpaRole> roles;

    @Override
    public UUID getId() {
        return id;
    }

    public JpaRight setId(UUID id) {
        this.id = id;

        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    public JpaRight setName(String name) {
        this.name = name;

        return this;
    }

    public Collection<JpaRole> getRoles() {
        return roles;
    }

    public JpaRight setRoles(Collection<JpaRole> roles) {
        this.roles = roles;

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

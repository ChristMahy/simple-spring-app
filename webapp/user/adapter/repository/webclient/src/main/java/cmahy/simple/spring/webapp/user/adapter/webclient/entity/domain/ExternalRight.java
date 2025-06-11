package cmahy.simple.spring.webapp.user.adapter.webclient.entity.domain;

import cmahy.simple.spring.webapp.user.kernel.domain.Right;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Collection;
import java.util.UUID;

public class ExternalRight implements Right {

    protected UUID id;
    protected String name;

    protected Collection<ExternalRole> roles;

    @Override
    public UUID getId() {
        return id;
    }

    public ExternalRight setId(UUID id) {
        this.id = id;

        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    public ExternalRight setName(String name) {
        this.name = name;

        return this;
    }

    public Collection<ExternalRole> getRoles() {
        return roles;
    }

    public ExternalRight setRoles(Collection<ExternalRole> roles) {
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

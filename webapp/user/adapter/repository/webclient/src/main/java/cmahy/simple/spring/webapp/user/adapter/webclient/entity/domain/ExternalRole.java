package cmahy.simple.spring.webapp.user.adapter.webclient.entity.domain;

import cmahy.simple.spring.webapp.user.kernel.domain.Role;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Collection;
import java.util.UUID;

public class ExternalRole implements Role {

    protected UUID id;
    protected String name;

    protected Collection<ExternalUser> users;

    @Override
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Collection<ExternalUser> getUsers() {
        return users;
    }

    public void setUsers(Collection<ExternalUser> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("id", getId())
            .append("name", getName())
            .toString();
    }
}

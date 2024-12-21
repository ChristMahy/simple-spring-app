package cmahy.webapp.user.adapter.webclient.entity;

import cmahy.webapp.user.kernel.domain.Role;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Collection;

public class ExternalRole implements Role {

    protected Long id;
    protected String name;

    protected Collection<ExternalUser> users;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

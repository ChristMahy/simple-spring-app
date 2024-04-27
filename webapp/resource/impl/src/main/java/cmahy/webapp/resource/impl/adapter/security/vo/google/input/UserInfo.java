package cmahy.webapp.resource.impl.adapter.security.vo.google.input;

import org.apache.commons.lang3.builder.ToStringBuilder;

public record UserInfo(
    String firstName,
    String lastName,
    String email,
    String id
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("email", email)
            .append("firstName", firstName)
            .append("lastName", lastName)
            .toString();
    }
}

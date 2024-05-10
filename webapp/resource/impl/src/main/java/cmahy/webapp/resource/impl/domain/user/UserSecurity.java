package cmahy.webapp.resource.impl.domain.user;

import jakarta.persistence.*;
import jakarta.validation.Constraint;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(
    uniqueConstraints = {
        @UniqueConstraint(name = "u_us_username_provider", columnNames = { "userName", "authProvider" })
    }
)
@DiscriminatorValue("1")
public class UserSecurity extends User {

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    @Column(columnDefinition = "smallint default 0")
    private Boolean isExpired;
    @Column(columnDefinition = "smallint default 0")
    private Boolean isLocked;
    @Column(columnDefinition = "smallint default 1")
    private Boolean isEnabled;
    @Column(columnDefinition = "smallint default 0")
    private Boolean isCredentialsExpired;

    public AuthProvider getAuthProvider() {
        return authProvider;
    }

    public void setAuthProvider(AuthProvider authProvider) {
        this.authProvider = authProvider;
    }

    public Boolean getExpired() {
        return isExpired;
    }

    public void setExpired(Boolean expired) {
        isExpired = expired;
    }

    public Boolean getLocked() {
        return isLocked;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public Boolean getCredentialsExpired() {
        return isCredentialsExpired;
    }

    public void setCredentialsExpired(Boolean credentialsExpired) {
        isCredentialsExpired = credentialsExpired;
    }

    @Override
    public String toString() {
        var builder = new ToStringBuilder(this, ToStringStyle.JSON_STYLE);

        return builder
            .append("userName", getUserName())
            .append("fullName", getFullName())
            .append("street", getStreet())
            .append("city", getCity())
            .append("state", getState())
            .append("zip", getZip())
            .append("phoneNumber", getPhoneNumber())
            .append("authProvider", getAuthProvider())
            .append("isExpired", getExpired())
            .append("isEnabled", getEnabled())
            .append("isCredentialsExpired", getCredentialsExpired())
            .build();
    }
}

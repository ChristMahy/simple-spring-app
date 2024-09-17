package cmahy.webapp.user.kernel.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class UserSecurity extends User {

    private AuthProvider authProvider;

    private Boolean isExpired;
    private Boolean isLocked;
    private Boolean isEnabled;
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

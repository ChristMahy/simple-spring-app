package cmahy.simple.spring.webapp.user.kernel.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Collection;
import java.util.UUID;

public class UserSecurityStub extends UserStub implements UserSecurity {

    private AuthProvider authProvider;

    private Boolean expired;

    private Boolean locked;

    private Boolean enabled;

    private Boolean credentialsExpired;

    @Override
    public AuthProvider getAuthProvider() {
        return authProvider;
    }

    public UserSecurityStub setAuthProvider(AuthProvider authProvider) {
        this.authProvider = authProvider;
        return this;
    }

    @Override
    public Boolean getExpired() {
        return expired;
    }

    public UserSecurityStub setExpired(Boolean expired) {
        this.expired = expired;
        return this;
    }

    @Override
    public Boolean getLocked() {
        return locked;
    }

    public UserSecurityStub setLocked(Boolean locked) {
        this.locked = locked;
        return this;
    }

    @Override
    public Boolean getEnabled() {
        return enabled;
    }

    public UserSecurityStub setEnabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Override
    public Boolean getCredentialsExpired() {
        return credentialsExpired;
    }

    public UserSecurityStub setCredentialsExpired(Boolean credentialsExpired) {
        this.credentialsExpired = credentialsExpired;
        return this;
    }

    @Override
    public UserSecurityStub setId(UUID id) {
        return (UserSecurityStub) super.setId(id);
    }

    @Override
    public UserSecurityStub setUserName(String userName) {
        return (UserSecurityStub) super.setUserName(userName);
    }

    @Override
    public UserSecurityStub setPassword(byte[] password) {
        return (UserSecurityStub) super.setPassword(password);
    }

    @Override
    public UserSecurityStub setFullName(String fullName) {
        return (UserSecurityStub) super.setFullName(fullName);
    }

    @Override
    public UserSecurityStub setStreet(String street) {
        return (UserSecurityStub) super.setStreet(street);
    }

    @Override
    public UserSecurityStub setCity(String city) {
        return (UserSecurityStub) super.setCity(city);
    }

    @Override
    public UserSecurityStub setState(String state) {
        return (UserSecurityStub) super.setState(state);
    }

    @Override
    public UserSecurityStub setZip(String zip) {
        return (UserSecurityStub) super.setZip(zip);
    }

    @Override
    public UserSecurityStub setPhoneNumber(String phoneNumber) {
        return (UserSecurityStub) super.setPhoneNumber(phoneNumber);
    }

    @Override
    public UserSecurityStub setRoles(Collection<RoleStub> roles) {
        return (UserSecurityStub) super.setRoles(roles);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("userName", getUserName())
            .append("password", "...")
            .append("fullName", getFullName())
            .append("street", getStreet())
            .append("city", getCity())
            .append("state", getState())
            .append("zip", getZip())
            .append("phoneNumber", getPhoneNumber())
            .append("authProvider", getAuthProvider())
            .append("isExpired", getExpired())
            .append("isLocked", getLocked())
            .append("isEnabled", getEnabled())
            .append("isCredentialsExpired", getCredentialsExpired())
            .toString();
    }
}

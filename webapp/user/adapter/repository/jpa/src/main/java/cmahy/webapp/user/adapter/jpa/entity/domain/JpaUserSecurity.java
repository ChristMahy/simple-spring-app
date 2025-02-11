package cmahy.webapp.user.adapter.jpa.entity.domain;

import cmahy.webapp.user.kernel.domain.AuthProvider;
import cmahy.webapp.user.kernel.domain.UserSecurity;
import jakarta.persistence.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Collection;
import java.util.UUID;

@Entity
@Table(
    name = "user_app",
    uniqueConstraints = @UniqueConstraint(
        name = "u_us_username_provider",
        columnNames = {"userName", "authProvider"}
    )
)
@DiscriminatorValue("1")
public class JpaUserSecurity extends JpaUser implements UserSecurity {

    private AuthProvider authProvider;

    private Boolean isExpired;
    private Boolean isLocked;
    private Boolean isEnabled;
    private Boolean isCredentialsExpired;

    @Override
    public AuthProvider getAuthProvider() {
        return authProvider;
    }

    public JpaUserSecurity setAuthProvider(AuthProvider authProvider) {
        this.authProvider = authProvider;
        return this;
    }

    @Override
    public Boolean getExpired() {
        return isExpired;
    }

    public JpaUserSecurity setExpired(Boolean expired) {
        isExpired = expired;
        return this;
    }

    @Override
    public Boolean getLocked() {
        return isLocked;
    }

    public JpaUserSecurity setLocked(Boolean locked) {
        isLocked = locked;
        return this;
    }

    @Override
    public Boolean getEnabled() {
        return isEnabled;
    }

    public JpaUserSecurity setEnabled(Boolean enabled) {
        isEnabled = enabled;
        return this;
    }

    @Override
    public Boolean getCredentialsExpired() {
        return isCredentialsExpired;
    }

    public JpaUserSecurity setCredentialsExpired(Boolean credentialsExpired) {
        isCredentialsExpired = credentialsExpired;
        return this;
    }

    @Override
    public JpaUserSecurity setId(UUID id) {
        return (JpaUserSecurity) super.setId(id);
    }

    @Override
    public JpaUserSecurity setUserName(String userName) {
        return (JpaUserSecurity) super.setUserName(userName);
    }

    @Override
    public JpaUserSecurity setPassword(byte[] password) {
        return (JpaUserSecurity) super.setPassword(password);
    }

    @Override
    public JpaUserSecurity setFullName(String fullName) {
        return (JpaUserSecurity) super.setFullName(fullName);
    }

    @Override
    public JpaUserSecurity setStreet(String street) {
        return (JpaUserSecurity) super.setStreet(street);
    }

    @Override
    public JpaUserSecurity setCity(String city) {
        return (JpaUserSecurity) super.setCity(city);
    }

    @Override
    public JpaUserSecurity setState(String state) {
        return (JpaUserSecurity) super.setState(state);
    }

    @Override
    public JpaUserSecurity setZip(String zip) {
        return (JpaUserSecurity) super.setZip(zip);
    }

    @Override
    public JpaUserSecurity setPhoneNumber(String phoneNumber) {
        return (JpaUserSecurity) super.setPhoneNumber(phoneNumber);
    }

    @Override
    public JpaUserSecurity setRoles(Collection<JpaRole> roles) {
        return (JpaUserSecurity) super.setRoles(roles);
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

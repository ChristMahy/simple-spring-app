package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain;

import cmahy.simple.spring.webapp.user.kernel.domain.AuthProvider;
import cmahy.simple.spring.webapp.user.kernel.domain.id.RoleId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Set;
import java.util.UUID;

@Table("user")
public class CassandraUserSecurityImpl extends CassandraUserImpl {

    private AuthProvider authProvider;

    private Boolean isExpired;
    private Boolean isLocked;
    private Boolean isEnabled;
    private Boolean isCredentialsExpired;

    @Override
    public CassandraUserSecurityImpl setId(UUID id) {
        super.setId(id);
        return this;
    }

    @Override
    public CassandraUserSecurityImpl setUserName(String userName) {
        super.setUserName(userName);
        return this;
    }

    @Override
    public CassandraUserSecurityImpl setPassword(byte[] password) {
        super.setPassword(password);
        return this;
    }

    @Override
    public CassandraUserSecurityImpl setFullName(String fullName) {
        super.setFullName(fullName);
        return this;
    }

    @Override
    public CassandraUserSecurityImpl setStreet(String street) {
        super.setStreet(street);
        return this;
    }

    @Override
    public CassandraUserSecurityImpl setCity(String city) {
        super.setCity(city);
        return this;
    }

    @Override
    public CassandraUserSecurityImpl setState(String state) {
        super.setState(state);
        return this;
    }

    @Override
    public CassandraUserSecurityImpl setZip(String zip) {
        super.setZip(zip);
        return this;
    }

    @Override
    public CassandraUserSecurityImpl setPhoneNumber(String phoneNumber) {
        super.setPhoneNumber(phoneNumber);
        return this;
    }

    @Override
    public CassandraUserSecurityImpl setCassandraRoleIds(Set<RoleId> cassandraRoleIds) {
        super.setCassandraRoleIds(cassandraRoleIds);
        return this;
    }

    @Override
    public CassandraUserSecurityImpl setDiscriminator(String discriminator) {
        return (CassandraUserSecurityImpl) super.setDiscriminator(discriminator);
    }

    public AuthProvider getAuthProvider() {
        return authProvider;
    }

    public CassandraUserSecurityImpl setAuthProvider(AuthProvider authProvider) {
        this.authProvider = authProvider;
        return this;
    }

    public Boolean getExpired() {
        return isExpired;
    }

    public CassandraUserSecurityImpl setExpired(Boolean expired) {
        isExpired = expired;
        return this;
    }

    public Boolean getLocked() {
        return isLocked;
    }

    public CassandraUserSecurityImpl setLocked(Boolean locked) {
        isLocked = locked;
        return this;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public CassandraUserSecurityImpl setEnabled(Boolean enabled) {
        isEnabled = enabled;
        return this;
    }

    public Boolean getCredentialsExpired() {
        return isCredentialsExpired;
    }

    public CassandraUserSecurityImpl setCredentialsExpired(Boolean credentialsExpired) {
        isCredentialsExpired = credentialsExpired;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("id", getId())
            .append("userName", getUserName())
            .append("fullName", getFullName())
            .append("street", getStreet())
            .append("city", getCity())
            .append("state", getState())
            .append("zip", getZip())
            .append("phoneNumber", getPhoneNumber())
            .append("cassandraRoleIds", getCassandraRoleIds())
            .append("authProvider", getAuthProvider())
            .append("isExpired", getExpired())
            .append("isLocked", getLocked())
            .append("isEnabled", getEnabled())
            .append("isCredentialsExpired", getCredentialsExpired())
            .toString();
    }
}

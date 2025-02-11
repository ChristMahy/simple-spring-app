package cmahy.webapp.user.adapter.cassandra.entity.domain;

import cmahy.webapp.user.kernel.domain.id.RoleId;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;

import java.util.Set;
import java.util.UUID;

public abstract class CassandraUser {

    @PrimaryKey
    private UUID id;
    private String userName;
    @CassandraType(type = CassandraType.Name.BLOB)
    private byte[] password;
    private String fullName;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phoneNumber;

    @CassandraType(type = CassandraType.Name.LIST, typeArguments = CassandraType.Name.BIGINT)
    private Set<RoleId> cassandraRoleIds;

    private String discriminator;

    public UUID getId() {
        return id;
    }

    public CassandraUser setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public CassandraUser setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public byte[] getPassword() {
        return password;
    }

    public CassandraUser setPassword(byte[] password) {
        this.password = password;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public CassandraUser setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public CassandraUser setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getCity() {
        return city;
    }

    public CassandraUser setCity(String city) {
        this.city = city;
        return this;
    }

    public String getState() {
        return state;
    }

    public CassandraUser setState(String state) {
        this.state = state;
        return this;
    }

    public String getZip() {
        return zip;
    }

    public CassandraUser setZip(String zip) {
        this.zip = zip;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public CassandraUser setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public Set<RoleId> getCassandraRoleIds() {
        return cassandraRoleIds;
    }

    public CassandraUser setCassandraRoleIds(Set<RoleId> cassandraRoleIds) {
        this.cassandraRoleIds = cassandraRoleIds;
        return this;
    }

    public String getDiscriminator() {
        return discriminator;
    }

    public CassandraUser setDiscriminator(String discriminator) {
        this.discriminator = discriminator;
        return this;
    }
}

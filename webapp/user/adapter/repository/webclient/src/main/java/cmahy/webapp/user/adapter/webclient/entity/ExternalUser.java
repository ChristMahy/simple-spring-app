package cmahy.webapp.user.adapter.webclient.entity;

import cmahy.webapp.user.kernel.domain.User;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Collection;

public class ExternalUser implements User {

    private Long id;
    private String userName;
    private byte[] password;
    private String fullName;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phoneNumber;
    private Collection<ExternalRole> roles;

    @Override
    public Long getId() {
        return id;
    }

    public ExternalUser setId(Long id) {
        this.id = id;

        return this;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    public ExternalUser setUserName(String userName) {
        this.userName = userName;

        return this;
    }

    @Override
    public byte[] getPassword() {
        return password;
    }

    public ExternalUser setPassword(byte[] password) {
        this.password = password;

        return this;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    public ExternalUser setFullName(String fullName) {
        this.fullName = fullName;

        return this;
    }

    @Override
    public String getStreet() {
        return street;
    }

    public ExternalUser setStreet(String street) {
        this.street = street;

        return this;
    }

    @Override
    public String getCity() {
        return city;
    }

    public ExternalUser setCity(String city) {
        this.city = city;

        return this;
    }

    @Override
    public String getState() {
        return state;
    }

    public ExternalUser setState(String state) {
        this.state = state;

        return this;
    }

    @Override
    public String getZip() {
        return zip;
    }

    public ExternalUser setZip(String zip) {
        this.zip = zip;

        return this;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ExternalUser setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;

        return this;
    }

    @Override
    public Collection<ExternalRole> getRoles() {
        return roles;
    }

    public ExternalUser setRoles(Collection<ExternalRole> roles) {
        this.roles = roles;

        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("userName", getUserName())
            .append("fullName", getFullName())
            .append("street", getStreet())
            .append("city", getCity())
            .append("state", getState())
            .append("zip", getZip())
            .append("phoneNumber", getPhoneNumber())
            .build();
    }
}

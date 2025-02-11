package cmahy.webapp.user.kernel.domain;

import java.util.Collection;
import java.util.UUID;

public class UserStub implements User {

    private UUID id;

    private String userName;

    private byte[] password;

    private String fullName;

    private String street;

    private String city;

    private String state;

    private String zip;

    private String phoneNumber;

    private Collection<RoleStub> roles;

    @Override
    public UUID getId() {
        return id;
    }

    public UserStub setId(UUID id) {
        this.id = id;
        return this;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    public UserStub setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    @Override
    public byte[] getPassword() {
        return password;
    }

    public UserStub setPassword(byte[] password) {
        this.password = password;
        return this;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    public UserStub setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    @Override
    public String getStreet() {
        return street;
    }

    public UserStub setStreet(String street) {
        this.street = street;
        return this;
    }

    @Override
    public String getCity() {
        return city;
    }

    public UserStub setCity(String city) {
        this.city = city;
        return this;
    }

    @Override
    public String getState() {
        return state;
    }

    public UserStub setState(String state) {
        this.state = state;
        return this;
    }

    @Override
    public String getZip() {
        return zip;
    }

    public UserStub setZip(String zip) {
        this.zip = zip;
        return this;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserStub setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public Collection<RoleStub> getRoles() {
        return roles;
    }

    public UserStub setRoles(Collection<RoleStub> roles) {
        this.roles = roles;
        return this;
    }
}

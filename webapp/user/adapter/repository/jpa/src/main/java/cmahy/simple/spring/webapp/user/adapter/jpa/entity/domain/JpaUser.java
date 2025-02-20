package cmahy.simple.spring.webapp.user.adapter.jpa.entity.domain;

import cmahy.simple.spring.webapp.user.kernel.domain.User;
import jakarta.persistence.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "user_app")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discriminator", discriminatorType = DiscriminatorType.INTEGER)
@DiscriminatorValue("0")
public class JpaUser implements User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String userName;
    private byte[] password;
    private String fullName;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phoneNumber;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_app_role",
        joinColumns = @JoinColumn(name = "user_app_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"),
        uniqueConstraints = @UniqueConstraint(
            name = "u_user_app_role",
            columnNames = {"user_app_id", "role_id"}
        )
    )
    private Collection<JpaRole> roles;

    @Override
    public UUID getId() {
        return id;
    }

    public JpaUser setId(UUID id) {
        this.id = id;

        return this;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    public JpaUser setUserName(String userName) {
        this.userName = userName;

        return this;
    }

    @Override
    public byte[] getPassword() {
        return password;
    }

    public JpaUser setPassword(byte[] password) {
        this.password = password;

        return this;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    public JpaUser setFullName(String fullName) {
        this.fullName = fullName;

        return this;
    }

    @Override
    public String getStreet() {
        return street;
    }

    public JpaUser setStreet(String street) {
        this.street = street;

        return this;
    }

    @Override
    public String getCity() {
        return city;
    }

    public JpaUser setCity(String city) {
        this.city = city;

        return this;
    }

    @Override
    public String getState() {
        return state;
    }

    public JpaUser setState(String state) {
        this.state = state;

        return this;
    }

    @Override
    public String getZip() {
        return zip;
    }

    public JpaUser setZip(String zip) {
        this.zip = zip;

        return this;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public JpaUser setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;

        return this;
    }

    @Override
    public Collection<JpaRole> getRoles() {
        return roles;
    }

    public JpaUser setRoles(Collection<JpaRole> roles) {
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

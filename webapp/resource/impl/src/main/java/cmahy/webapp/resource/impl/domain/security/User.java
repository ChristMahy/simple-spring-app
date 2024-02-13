//package cmahy.webapp.resource.impl.domain.security;
//
//import cmahy.webapp.resource.impl.security.vo.AuthProvider;
//import jakarta.persistence.*;
//import org.apache.commons.lang3.builder.ToStringBuilder;
//import org.apache.commons.lang3.builder.ToStringStyle;
//
//import java.util.Collection;
//
//@Entity
//@Table(name = "user_app")
//public record User(
//    @Id
//    @GeneratedValue
//    Long id,
//    String userName,
//    byte[] password,
//    String fullName,
//    String street,
//    String city,
//    String state,
//    String zip,
//    String phoneNumber,
//    @Enumerated(EnumType.STRING)
//    AuthProvider authProvider,
//
//    @Column(columnDefinition = "smallint default 0")
//    Boolean isExpired,
//    @Column(columnDefinition = "smallint default 0")
//    Boolean isLocked,
//    @Column(columnDefinition = "smallint default 1")
//    Boolean isEnabled,
//    @Column(columnDefinition = "smallint default 0")
//    Boolean isCredentialsExpired,
//
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//        name = "user_app_role",
//        joinColumns = {
//            @JoinColumn(name = "user_app_id")
//        },
//        inverseJoinColumns = {
//            @JoinColumn(name = "role_id")
//        },
//        uniqueConstraints = {
//            @UniqueConstraint(
//                name = "u_user_app_role",
//                columnNames = {"user_app_id", "role_id"}
//            )
//        }
//    )
//    Collection<Role> roles
//) {
//    @Override
//    public String toString() {
//        var builder = new ToStringBuilder(this, ToStringStyle.JSON_STYLE);
//
//        return builder
//            .append("userName", userName())
//            .append("fullName", fullName())
//            .append("street", street())
//            .append("city", city())
//            .append("state", state())
//            .append("zip", zip())
//            .append("phoneNumber", phoneNumber())
//            .append("authProvider", authProvider())
//            .append("isExpired", isExpired())
//            .append("isEnabled", isEnabled())
//            .append("isCredentialsExpired", isCredentialsExpired())
//            .build();
//    }
//}

package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain;

import cmahy.simple.spring.webapp.user.kernel.domain.id.RoleId;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Set;
import java.util.UUID;

@Table("user")
public class CassandraUserImpl extends CassandraUser {

    @Override
    public CassandraUserImpl setCassandraRoleIds(Set<RoleId> cassandraRoleIds) {
        return (CassandraUserImpl) super.setCassandraRoleIds(cassandraRoleIds);
    }

    @Override
    public CassandraUserImpl setCity(String city) {
        return (CassandraUserImpl) super.setCity(city);
    }

    @Override
    public CassandraUserImpl setDiscriminator(String discriminator) {
        return (CassandraUserImpl) super.setDiscriminator(discriminator);
    }

    @Override
    public CassandraUserImpl setFullName(String fullName) {
        return (CassandraUserImpl) super.setFullName(fullName);
    }

    @Override
    public CassandraUserImpl setId(UUID id) {
        return (CassandraUserImpl) super.setId(id);
    }

    @Override
    public CassandraUserImpl setPassword(byte[] password) {
        return (CassandraUserImpl) super.setPassword(password);
    }

    @Override
    public CassandraUserImpl setPhoneNumber(String phoneNumber) {
        return (CassandraUserImpl) super.setPhoneNumber(phoneNumber);
    }

    @Override
    public CassandraUserImpl setState(String state) {
        return (CassandraUserImpl) super.setState(state);
    }

    @Override
    public CassandraUserImpl setStreet(String street) {
        return (CassandraUserImpl) super.setStreet(street);
    }

    @Override
    public CassandraUserImpl setUserName(String userName) {
        return (CassandraUserImpl) super.setUserName(userName);
    }

    @Override
    public CassandraUserImpl setZip(String zip) {
        return (CassandraUserImpl) super.setZip(zip);
    }
}

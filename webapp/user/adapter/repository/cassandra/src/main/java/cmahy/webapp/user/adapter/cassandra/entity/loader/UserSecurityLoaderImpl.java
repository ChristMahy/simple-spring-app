package cmahy.webapp.user.adapter.cassandra.entity.loader;

import cmahy.webapp.user.adapter.cassandra.entity.proxy.CassandraRoleProxy;
import cmahy.webapp.user.kernel.domain.id.RoleId;
import jakarta.inject.Named;

import java.util.Set;

@Named
public class UserSecurityLoaderImpl implements UserSecurityLoader {

    private final UserLoader userLoader;

    public UserSecurityLoaderImpl(
        UserLoader userLoader
    ) {
        this.userLoader = userLoader;
    }

    @Override
    public Set<CassandraRoleProxy> loadRoles(Set<RoleId> roleIds) {
        return userLoader.loadRoles(roleIds);
    }
}

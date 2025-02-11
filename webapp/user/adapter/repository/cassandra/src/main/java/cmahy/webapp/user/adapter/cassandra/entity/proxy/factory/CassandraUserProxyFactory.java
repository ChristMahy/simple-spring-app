package cmahy.webapp.user.adapter.cassandra.entity.proxy.factory;

import cmahy.webapp.user.adapter.cassandra.entity.domain.CassandraUserImpl;
import cmahy.webapp.user.adapter.cassandra.entity.loader.UserLoader;
import cmahy.webapp.user.adapter.cassandra.entity.proxy.CassandraUserProxy;
import jakarta.inject.Named;

@Named
public class CassandraUserProxyFactory {

    private final UserLoader userLoader;

    public CassandraUserProxyFactory(UserLoader userLoader) {
        this.userLoader = userLoader;
    }

    public CassandraUserProxy create(CassandraUserImpl user) {
        return new CassandraUserProxy(user, userLoader);
    }
}

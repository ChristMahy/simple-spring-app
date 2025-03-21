package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.builder.factory;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.builder.CassandraUserBuilder;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraUserProxy;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraUserProxyFactory;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.factory.UserBuilderFactory;
import jakarta.inject.Named;

@Named
public class CassandraUserBuilderFactory implements UserBuilderFactory<CassandraUserProxy> {

    private final CassandraUserProxyFactory cassandraUserProxyFactory;

    public CassandraUserBuilderFactory(
        CassandraUserProxyFactory cassandraUserProxyFactory
    ) {
        this.cassandraUserProxyFactory = cassandraUserProxyFactory;
    }

    @Override
    public CassandraUserBuilder create() {
        return new CassandraUserBuilder(cassandraUserProxyFactory);
    }

    @Override
    public CassandraUserBuilder create(CassandraUserProxy user) {
        return new CassandraUserBuilder(cassandraUserProxyFactory, user);
    }
}

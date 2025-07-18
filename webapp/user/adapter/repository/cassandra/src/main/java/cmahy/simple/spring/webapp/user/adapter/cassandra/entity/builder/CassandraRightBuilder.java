package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.builder;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraRight;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraRightProxy;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraRoleProxy;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraRightProxyFactory;
import cmahy.simple.spring.webapp.user.kernel.domain.Role;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.RightBuilder;

import java.util.Collection;
import java.util.Optional;

public class CassandraRightBuilder implements RightBuilder<CassandraRightProxy> {

    private final CassandraRightProxyFactory rightProxyFactory;
    private Optional<CassandraRightProxy> originalRight = Optional.empty();

    public CassandraRightBuilder(CassandraRightProxyFactory rightProxyFactory) {
        this.rightProxyFactory = rightProxyFactory;
    }

    public CassandraRightBuilder(CassandraRightProxyFactory rightProxyFactory, CassandraRightProxy right) {
        this(rightProxyFactory);

        this.originalRight = Optional.ofNullable(right);

        this.originalRight.ifPresent(originalRight -> {
            this.name(originalRight.getName())
                .roles(originalRight.getRoles());
        });
    }

    private String name;
    private Collection<CassandraRoleProxy> roles;

    @Override
    public CassandraRightBuilder name(String name) {
        this.name = name;

        return this;
    }

    @Override
    public <ROLE extends Role> CassandraRightBuilder roles(Collection<ROLE> roles) {
        this.roles = (Collection<CassandraRoleProxy>) roles;

        return this;
    }

    @Override
    public CassandraRightProxy build() {
        return this.originalRight
            .orElseGet(() -> rightProxyFactory.create(new CassandraRight()))
            .setName(name)
            .setRoles(roles);
    }
}

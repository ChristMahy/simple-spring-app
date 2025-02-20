package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.loader;

import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.CassandraTacoProxy;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.id.TacoId;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraUserProxy;
import cmahy.simple.spring.webapp.user.kernel.domain.id.UserId;

import java.util.List;
import java.util.Optional;

public interface ClientOrderLoader {

    List<CassandraTacoProxy> loadTacos(List<TacoId> tacoIds);

    Optional<CassandraUserProxy> loadUser(UserId userId);
}

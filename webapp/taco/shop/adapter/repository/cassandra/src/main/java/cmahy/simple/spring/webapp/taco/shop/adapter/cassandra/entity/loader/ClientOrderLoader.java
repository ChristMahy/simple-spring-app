package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.loader;

import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraTaco;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.id.TacoId;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraUserImpl;
import cmahy.simple.spring.webapp.user.kernel.domain.id.UserId;

import java.util.List;
import java.util.Optional;

public interface ClientOrderLoader extends Loader {

    List<CassandraTaco> loadTacos(List<TacoId> tacoIds);

    Optional<CassandraUserImpl> loadUser(UserId userId);
}

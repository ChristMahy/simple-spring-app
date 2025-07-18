package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.loader;

import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraClientOrder;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraTaco;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.repository.cassandra.CassandraTacoRepository;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.id.TacoId;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraUserImpl;
import cmahy.simple.spring.webapp.user.adapter.cassandra.repository.cassandra.CassandraUserRepositoryImpl;
import cmahy.simple.spring.webapp.user.kernel.domain.id.UserId;
import jakarta.inject.Named;

import java.util.List;
import java.util.Optional;

@Named
public class ClientOrderLoaderImpl implements ClientOrderLoader {

    private final CassandraTacoRepository tacoRepository;
    private final CassandraUserRepositoryImpl userRepository;

    public ClientOrderLoaderImpl(
        CassandraTacoRepository tacoRepository,
        CassandraUserRepositoryImpl userRepository
    ) {
        this.tacoRepository = tacoRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Class<CassandraClientOrder> entityClass() {
        return CassandraClientOrder.class;
    }

    @Override
    public List<CassandraTaco> loadTacos(List<TacoId> tacoIds) {
        return tacoRepository.findAllById(tacoIds);
    }

    @Override
    public Optional<CassandraUserImpl> loadUser(UserId userId) {
        return userRepository.findById(userId);
    }
}

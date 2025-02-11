package cmahy.webapp.taco.shop.adapter.cassandra.entity.loader;

import cmahy.webapp.taco.shop.adapter.cassandra.entity.proxy.CassandraTacoProxy;
import cmahy.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.CassandraTacoProxyFactory;
import cmahy.webapp.taco.shop.adapter.cassandra.repository.cassandra.CassandraTacoRepository;
import cmahy.webapp.taco.shop.kernel.domain.id.TacoId;
import cmahy.webapp.user.adapter.cassandra.entity.proxy.CassandraUserProxy;
import cmahy.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraUserProxyFactory;
import cmahy.webapp.user.adapter.cassandra.repository.cassandra.CassandraUserRepositoryImpl;
import cmahy.webapp.user.kernel.domain.id.UserId;
import jakarta.inject.Named;
import org.springframework.context.annotation.Lazy;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Named
public class ClientOrderLoaderImpl implements ClientOrderLoader {

    private final CassandraTacoRepository tacoRepository;
    private final CassandraUserRepositoryImpl userRepository;

    private final CassandraTacoProxyFactory tacoProxyFactory;
    private final CassandraUserProxyFactory userProxyFactory;

    public ClientOrderLoaderImpl(
        CassandraTacoRepository tacoRepository,
        CassandraUserRepositoryImpl userRepository,
        @Lazy CassandraTacoProxyFactory tacoProxyFactory,
        @Lazy CassandraUserProxyFactory userProxyFactory
    ) {
        this.tacoRepository = tacoRepository;
        this.userRepository = userRepository;
        this.tacoProxyFactory = tacoProxyFactory;
        this.userProxyFactory = userProxyFactory;
    }

    @Override
    public List<CassandraTacoProxy> loadTacos(List<TacoId> tacoIds) {
        return tacoRepository.findAllById(tacoIds).stream()
            .map(tacoProxyFactory::create)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<CassandraUserProxy> loadUser(UserId userId) {
        return userRepository.findById(userId)
            .map(userProxyFactory::create);
    }
}

package cmahy.springapp.resourceserver.repository;

import cmahy.springapp.resourceserver.domain.TacoOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaOrderRepository extends OrderRepository, CrudRepository<TacoOrder, Long> {
}

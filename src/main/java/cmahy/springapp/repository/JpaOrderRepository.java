package cmahy.springapp.repository;

import cmahy.springapp.domain.TacoOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaOrderRepository extends OrderRepository, CrudRepository<TacoOrder, Long> {
}

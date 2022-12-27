package cmahy.springapp.repository;

import cmahy.springapp.domain.TacoOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CassandraOrderRepository extends OrderRepository, CrudRepository<TacoOrder, UUID> {
}

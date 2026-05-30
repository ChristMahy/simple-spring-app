package cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.repository.cassandra;

import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraIngredient;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CassandraIngredientTestRepository extends CassandraRepository<CassandraIngredient, UUID> {
}

package cmahy.springapp.repository;

import cmahy.springapp.domain.Taco;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CassandraTacoRepository extends TacoRepository, CrudRepository<Taco, UUID> {
}

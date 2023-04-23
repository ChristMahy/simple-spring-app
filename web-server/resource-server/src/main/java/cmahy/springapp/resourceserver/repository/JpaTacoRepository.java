package cmahy.springapp.resourceserver.repository;

import cmahy.springapp.resourceserver.domain.Taco;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTacoRepository extends TacoRepository, CrudRepository<Taco, Long> {
}

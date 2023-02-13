package cmahy.springapp.repository;

import cmahy.springapp.domain.Taco;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTacoRepository extends TacoRepository, CrudRepository<Taco, Long> {
}

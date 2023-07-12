package cmahy.springapp.reactive.repository;

import cmahy.springapp.reactive.domain.Todo;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends ReactiveCrudRepository<Todo, Long> {
}

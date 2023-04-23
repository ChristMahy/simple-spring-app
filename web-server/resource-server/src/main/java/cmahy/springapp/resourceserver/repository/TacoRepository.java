package cmahy.springapp.resourceserver.repository;

import cmahy.springapp.resourceserver.domain.Taco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TacoRepository {
    Page<Taco> findAll(Pageable pageable);

    Taco save(Taco taco1);

    Optional<Taco> findById(Long id);
}

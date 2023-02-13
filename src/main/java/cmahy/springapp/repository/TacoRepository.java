package cmahy.springapp.repository;

import cmahy.springapp.domain.Taco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TacoRepository {
    Page<Taco> findAll(Pageable pageable);

    Taco save(Taco taco1);
}

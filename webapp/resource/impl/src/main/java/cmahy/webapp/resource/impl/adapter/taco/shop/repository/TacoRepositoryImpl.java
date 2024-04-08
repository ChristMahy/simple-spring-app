package cmahy.webapp.resource.impl.adapter.taco.shop.repository;

import cmahy.webapp.resource.impl.application.taco.shop.repository.TacoRepository;
import cmahy.webapp.resource.impl.domain.taco.Taco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TacoRepositoryImpl extends TacoRepository, JpaRepository<Taco, Long> {
}

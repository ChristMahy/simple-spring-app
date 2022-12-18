package cmahy.springapp.repository;

import cmahy.springapp.domain.TacoOrder;

public interface OrderRepository {
    TacoOrder save(TacoOrder tacoOrder);
}

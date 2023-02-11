package cmahy.springapp.repository;

import cmahy.springapp.domain.TacoOrder;
import cmahy.springapp.domain.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderRepository {
    TacoOrder save(TacoOrder tacoOrder);

    List<TacoOrder> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
}

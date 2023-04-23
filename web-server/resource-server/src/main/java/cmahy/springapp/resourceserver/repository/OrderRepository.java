package cmahy.springapp.resourceserver.repository;

import cmahy.springapp.resourceserver.domain.TacoOrder;
import cmahy.springapp.resourceserver.domain.User;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    TacoOrder save(TacoOrder tacoOrder);

    List<TacoOrder> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);

    Optional<TacoOrder> findById(Long orderId);

    void deleteById(Long id);
}

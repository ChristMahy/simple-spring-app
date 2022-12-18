package cmahy.springapp.repository;

import cmahy.springapp.domain.IngredientRef;
import cmahy.springapp.domain.Taco;
import cmahy.springapp.domain.TacoOrder;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static java.sql.Types.*;

@Repository
public class JdbcOrderRepository implements OrderRepository {
    private final JdbcOperations jdbcOperations;

    public JdbcOrderRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    @Transactional
    public TacoOrder save(TacoOrder tacoOrder) {
        PreparedStatementCreatorFactory pscf =
            new PreparedStatementCreatorFactory(
                "insert into taco_order (" +
                    "delivery_name, delivery_street, delivery_city, delivery_state, delivery_zip, " +
                    "cc_number, cc_expiration, cc_cvv, placed_at" +
                    ") values (" +
                    "?, ?, ?, ?, ?, " +
                    "?, ?, ?, ?" +
                    ")",
                VARCHAR, VARCHAR, VARCHAR, VARCHAR, VARCHAR,
                VARCHAR, VARCHAR, VARCHAR, TIMESTAMP
            );

        pscf.setReturnGeneratedKeys(true);

        tacoOrder.setPlacedAt(new Date());

        PreparedStatementCreator psc =
            pscf.newPreparedStatementCreator(List.of(
                tacoOrder.getDeliveryName(),
                tacoOrder.getDeliveryStreet(),
                tacoOrder.getDeliveryCity(),
                tacoOrder.getDeliveryState(),
                tacoOrder.getDeliveryZip(),
                tacoOrder.getCcNumber(),
                tacoOrder.getCcExpiration(),
                tacoOrder.getCcCVV(),
                tacoOrder.getPlacedAt()
            ));

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcOperations.update(psc, keyHolder);

        long orderId = keyHolder.getKey().longValue();

        tacoOrder.setId(orderId);

        int index = 0;
        for (Taco taco : tacoOrder.getTacos()) {
            saveTaco(orderId, index++, taco);
        }

        return tacoOrder;
    }

    private Taco saveTaco(Long orderId, int orderKey, Taco taco) {
        taco.setCreatedAt(new Date());

        PreparedStatementCreatorFactory pscf =
            new PreparedStatementCreatorFactory(
                "insert into taco (" +
                    "name, created_at, taco_order, taco_order_key" +
                    ") values (" +
                    "?, ?, ?, ?" +
                    ")",
                VARCHAR, TIMESTAMP, BIGINT, BIGINT
            );

        pscf.setReturnGeneratedKeys(true);

        PreparedStatementCreator psc =
            pscf.newPreparedStatementCreator(List.of(
                taco.getName(),
                taco.getCreatedAt(),
                orderId,
                orderKey
            ));

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcOperations.update(psc, keyHolder);

        long tacoId = keyHolder.getKey().longValue();

        taco.setId(tacoId);

        saveIngredientRefs(tacoId, taco.getIngredients());

        return taco;
    }

    private void saveIngredientRefs(Long tacoId, List<IngredientRef> ingredients) {
        int key = 0;

        for (IngredientRef ingredientRef : ingredients) {
            jdbcOperations.update(
                "insert into ingredient_ref (ingredient, taco, taco_key) values (?, ?, ?)",
                ingredientRef.getIngredient(), tacoId, key++
            );
        }
    }
}

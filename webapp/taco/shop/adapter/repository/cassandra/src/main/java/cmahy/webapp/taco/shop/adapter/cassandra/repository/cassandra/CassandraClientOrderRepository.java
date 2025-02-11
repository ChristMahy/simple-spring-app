package cmahy.webapp.taco.shop.adapter.cassandra.repository.cassandra;

import cmahy.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraClientOrder;
import cmahy.webapp.user.kernel.domain.id.UserId;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.UUID;

public interface CassandraClientOrderRepository extends CassandraRepository<CassandraClientOrder, UUID> {

    @AllowFiltering
    Slice<CassandraClientOrder> findByUserId(UserId userId, Pageable pageable);
}

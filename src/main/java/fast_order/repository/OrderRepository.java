package fast_order.repository;

import fast_order.commons.enums.OrderStatus;
import fast_order.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    @Modifying
    @Transactional
    @Query("UPDATE OrderEntity ord SET ord.status = :status, ord.amount = 0 WHERE ord.id = :id")
    int cancelOrder(@Param("id") UUID id, @Param("status") OrderStatus status);
}

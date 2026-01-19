package uz.spring.delivery.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.spring.delivery.constant.enums.Status;
import uz.spring.delivery.entity.OrderEntity;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity,Long> {

    @EntityGraph(value = "Order.withMerchant", type = EntityGraph.EntityGraphType.FETCH)
    Optional<OrderEntity> findWithMerchantById(Long id);

    @EntityGraph(attributePaths = {"merchant"})
    List<OrderEntity> findByStatus(Status status);

    @Query("""
                SELECT o 
                FROM OrderEntity o
               WHERE o.status = :status""")
    Optional<OrderEntity> findByCode(@Param("status") Status status);
}

package uz.spring.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.spring.delivery.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity,Long> {
}

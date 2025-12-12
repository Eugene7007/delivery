package uz.spring.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.spring.delivery.entity.MerchantEntity;

public interface MerchantRepository extends JpaRepository<MerchantEntity,Long> {
}

package uz.spring.delivery.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import uz.spring.delivery.repository.MerchantRepository;
import uz.spring.delivery.service.MerchantService;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class MerchantServiceImpl implements MerchantService {

    MerchantRepository merchantRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void update(Long id, String name) {
        var merchant = merchantRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Merchant not found"));

        merchant.setName(name);
    }
}

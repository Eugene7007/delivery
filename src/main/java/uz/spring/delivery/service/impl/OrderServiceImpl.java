package uz.spring.delivery.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.spring.delivery.component.adapter.GcpAdapter;
import uz.spring.delivery.dto.request.OrderRequestDto;
import uz.spring.delivery.dto.response.OrderResponseDto;
import uz.spring.delivery.entity.BaseEntity;
import uz.spring.delivery.exception.OrderNotFoundException;
import uz.spring.delivery.mapper.OrderMapper;
import uz.spring.delivery.repository.OrderRepository;
import uz.spring.delivery.service.OrderService;

import static uz.spring.delivery.constant.Constant.ORDER_REDIS_KEYS;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements OrderService {

    GcpAdapter gcpAdapter;
    OrderMapper orderMapper;
    OrderRepository orderRepository;

    @Transactional
    public OrderResponseDto create(OrderRequestDto requestDto) {
        var entity = orderMapper.toEntity(requestDto);
        var result = orderRepository.save(entity);

        orderRepository.findWithMerchantById(1L).map(orderMapper::toDto);
        return orderMapper.toDto(result);
    }

    @Override
    @Transactional
    @CacheEvict(value = ORDER_REDIS_KEYS, key = "'order:' + #id")
    public void delete(Long id) {
        var order = orderRepository
                .findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id.toString()));
        order.setActive(false);
    }

    @Override
    @Cacheable(
            value = ORDER_REDIS_KEYS,
            key = "'order:' + #id",
            unless = "#result == null"
    )
    @Transactional(readOnly = true)
    public OrderResponseDto get(Long id) {
        var result = orderRepository
                .findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id.toString()));
        log.info("Order: {}", result.toString());

        return orderRepository
                .findById(id)
                .filter(BaseEntity::isActive)
                .map(orderMapper::toDto)
                .orElseThrow(() -> new OrderNotFoundException(id.toString()));
    }

    @Override
    @CacheEvict(value = ORDER_REDIS_KEYS, key = "'order:' + #id")
    @Transactional
    public void update(String description, Long id) {
        var order = orderRepository
                .findById(id)
                .orElseThrow(() -> new OrderNotFoundException(description));
        order.setDescription(description);
    }

    @Override
    public OrderResponseDto testGcp(Long id) {
        gcpAdapter.getUserInfo();

        return new OrderResponseDto(1L, "", "");
    }
}

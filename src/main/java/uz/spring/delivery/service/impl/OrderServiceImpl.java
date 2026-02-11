package uz.spring.delivery.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.spring.delivery.component.Timed;
import uz.spring.delivery.component.adapter.GcpAdapter;
import uz.spring.delivery.dto.request.OrderRequestDto;
import uz.spring.delivery.dto.response.OrderResponseDto;
import uz.spring.delivery.entity.BaseEntity;
import uz.spring.delivery.exception.OrderNotFoundException;
import uz.spring.delivery.mapper.OrderMapper;
import uz.spring.delivery.repository.OrderRepository;
import uz.spring.delivery.service.OrderService;
import uz.spring.delivery.utils.PaginationValidator;

import static uz.spring.delivery.constant.Constant.ORDER_REDIS_KEYS;

/**
 * Service implementation for handling operations related to orders.
 * This class provides functionality for creating, retrieving, updating, and deleting orders,
 * as well as interacting with external systems for additional operations.
 *
 * It utilizes transactional behavior, caching mechanisms, and integrates with a GCP adapter.
 * The methods herein make use of injected dependencies including the OrderMapper for entity-DTO conversions,
 * the OrderRepository for database interactions, and the GcpAdapter for external HTTP communications.
 *
 * Annotations:
 * - @Slf4j: Provides logging capabilities.
 * - @Service: Indicates this class is a Spring service component.
 * - @RequiredArgsConstructor: Generates a constructor with required arguments for final fields.
 * - @FieldDefaults: Sets the access level for fields as private and final.
 */
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

    @Timed
    @Override
    @Cacheable(
            value = ORDER_REDIS_KEYS,
            key = "'order:' + #id",
            unless = "#result == null"
    )
    @Transactional(readOnly = true)
    public OrderResponseDto get(Long id) {
        log.info("get order {}", id);
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

    @Override
    public Page<OrderResponseDto> testPagination(int pageNumber, int size) {
        var pageable = PaginationValidator.validate(pageNumber, size);

        return orderRepository
                .findAll(pageable)
                .map(orderMapper::toDto);
    }
}

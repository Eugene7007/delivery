package uz.spring.delivery.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import uz.spring.delivery.dto.request.OrderRequestDto;
import uz.spring.delivery.dto.response.OrderResponseDto;
import uz.spring.delivery.entity.BaseEntity;
import uz.spring.delivery.entity.OrderEntity;
import uz.spring.delivery.exception.OrderNotFoundException;
import uz.spring.delivery.exception.TransactionExampleException;
import uz.spring.delivery.mapper.OrderMapper;
import uz.spring.delivery.repository.OrderRepository;
import uz.spring.delivery.service.MerchantService;
import uz.spring.delivery.service.OrderService;

import java.sql.SQLDataException;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements OrderService {

    OrderMapper orderMapper;
    MerchantService merchantService;
    OrderRepository orderRepository;

    @Transactional
    public OrderResponseDto create(OrderRequestDto requestDto) {
        var entity = orderMapper.toEntity(requestDto);
        var result = orderRepository.save(entity);

        return orderMapper.toDto(result);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        var order = orderRepository
                .findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id.toString()));
        order.setActive(false);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDto get(Long id) {
        var result = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id.toString()));
        log.info("Order: {}", result.toString());

        return orderRepository.findById(id)
                .filter(BaseEntity::isActive)
                .map(orderMapper::toDto)
                .orElseThrow(() -> new OrderNotFoundException(id.toString()));
    }

    @Override
    @Transactional(rollbackFor = SQLDataException.class)
    public void update01(String description, Long id) throws SQLDataException {
        var order = orderRepository
                .findById(id)
                .orElseThrow(() -> new OrderNotFoundException(description));
        order.setDescription(description);

        if (id == 1)
            throw new SQLDataException("Error");

    }

    @Override
    @Transactional
    public void update02(String description, Long id) {
        var order = orderRepository
                .findById(id)
                .orElseThrow(() -> new OrderNotFoundException(description));
        order.setDescription(description);

        if (id == 1)
            throw new TransactionExampleException("Error");
    }

    @Override
    @Transactional
    public void update03(String description, Long id) {
        var order = orderRepository
                .findById(id)
                .orElseThrow(() -> new OrderNotFoundException(description));
        order.setDescription(description);

        updateMerchant(1L, "John");

        if (id == 1)
            throw new TransactionExampleException("Error");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void updateMerchant(Long id, String name) {
        merchantService.update(id, name);
    }
}

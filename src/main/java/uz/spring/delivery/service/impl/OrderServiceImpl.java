package uz.spring.delivery.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.spring.delivery.dto.request.OrderRequestDto;
import uz.spring.delivery.dto.response.OrderResponseDto;
import uz.spring.delivery.entity.BaseEntity;
import uz.spring.delivery.exception.OrderNotFoundException;
import uz.spring.delivery.mapper.OrderMapper;
import uz.spring.delivery.repository.OrderRepository;
import uz.spring.delivery.service.OrderService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements OrderService {

    OrderMapper orderMapper;
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
        return orderRepository.findById(id)
                .filter(BaseEntity::isActive)
                .map(orderMapper::toDto)
                .orElseThrow(() -> new OrderNotFoundException(id.toString()));
    }

    @Override
    @Transactional
    public void update(String description, Long id) {
        var order = orderRepository
                .findById(id)
                .orElseThrow(() -> new OrderNotFoundException(description));
        order.setDescription(description);
    }
}

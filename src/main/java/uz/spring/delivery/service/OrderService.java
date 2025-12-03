package uz.spring.delivery.service;

import uz.spring.delivery.dto.request.OrderRequestDto;
import uz.spring.delivery.dto.response.OrderResponseDto;

public interface OrderService {

    OrderResponseDto create(OrderRequestDto requestDto);

    void delete(Long id);
    OrderResponseDto get(Long id);
    void update(String description, Long id);
}

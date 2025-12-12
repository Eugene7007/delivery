package uz.spring.delivery.service;

import uz.spring.delivery.dto.request.OrderRequestDto;
import uz.spring.delivery.dto.response.OrderResponseDto;

import java.sql.SQLDataException;

public interface OrderService {

    OrderResponseDto create(OrderRequestDto requestDto);

    void delete(Long id);
    OrderResponseDto get(Long id);
    void update01(String description, Long id) throws SQLDataException;
    void update02(String description, Long id);
    void update03(String description, Long id);
}

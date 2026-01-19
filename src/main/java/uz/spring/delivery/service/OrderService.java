package uz.spring.delivery.service;

import org.springframework.data.domain.Page;
import uz.spring.delivery.dto.request.OrderRequestDto;
import uz.spring.delivery.dto.response.OrderResponseDto;

import java.sql.SQLDataException;

public interface OrderService {

    OrderResponseDto create(OrderRequestDto requestDto);

    void delete(Long id);
    OrderResponseDto get(Long id);
    void update(String description, Long id) throws SQLDataException;

    OrderResponseDto testGcp(Long id);

    Page<OrderResponseDto> testPagination(int size, int page);
}

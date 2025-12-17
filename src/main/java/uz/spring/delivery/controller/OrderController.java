package uz.spring.delivery.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.spring.delivery.dto.request.OrderRequestDto;
import uz.spring.delivery.dto.response.OrderResponseDto;
import uz.spring.delivery.service.OrderService;

import java.sql.SQLDataException;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/delivery/orders")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class OrderController {

    OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> create(@RequestBody OrderRequestDto requestDto) {
        return ResponseEntity.ok(orderService.create(requestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.get(id));
    }

    @GetMapping("/gcp/{id}")
    public ResponseEntity<OrderResponseDto> testGcp(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.testGcp(id));
    }

    @PutMapping("/update-01/{id}")
    public void update01(@RequestParam String description, @PathVariable Long id) throws SQLDataException {
        orderService.update(description, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        orderService.delete(id);
    }
}

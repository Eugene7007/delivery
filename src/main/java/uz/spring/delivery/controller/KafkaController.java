package uz.spring.delivery.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.spring.delivery.service.KafkaService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/kafka")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class KafkaController {

    KafkaService kafkaService;

    @PostMapping("/{message}")
    public ResponseEntity<Void> send(@PathVariable String message) {
        kafkaService.sendMessage(message);
        return ResponseEntity.noContent().build();
    }
}

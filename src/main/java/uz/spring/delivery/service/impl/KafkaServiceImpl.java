package uz.spring.delivery.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import uz.spring.delivery.component.kafka.producer.OrderProducer;
import uz.spring.delivery.dto.OrderDto;
import uz.spring.delivery.service.KafkaService;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class KafkaServiceImpl implements KafkaService {

    OrderProducer orderProducer;

    @Override
    public void sendMessage(String message) {
        orderProducer.sendMessage(new OrderDto("order123", "corId", message));
    }
}

package uz.spring.delivery.service;

public interface KafkaService {

    void sendMessage(String message);
}

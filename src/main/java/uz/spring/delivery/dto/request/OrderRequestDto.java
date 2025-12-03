package uz.spring.delivery.dto.request;

public record OrderRequestDto(
        double latitudeFrom,
        double longitudeFrom,
        double latitudeTo,
        double longitudeTo,
        String description
) {}

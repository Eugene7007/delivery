package uz.spring.delivery.dto.response;

public record OrderResponseDto(
   Long id,
   String merchant,
   String description
) {}

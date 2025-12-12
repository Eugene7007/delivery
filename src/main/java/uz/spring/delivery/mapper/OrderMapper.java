package uz.spring.delivery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.spring.delivery.dto.request.OrderRequestDto;
import uz.spring.delivery.dto.response.OrderResponseDto;
import uz.spring.delivery.entity.OrderEntity;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", constant = "NEW")
    OrderEntity toEntity(OrderRequestDto dto);

    @Mapping(target = "merchant", source = "merchant.name")
    OrderResponseDto toDto(OrderEntity entity);
}

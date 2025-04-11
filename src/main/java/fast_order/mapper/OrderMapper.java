package fast_order.mapper;

import fast_order.dto.OrderTO;
import fast_order.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mappings(
        {
            @Mapping(target = "user.id", source = "userId"),
            @Mapping(target = "product.id", source = "productId"),
            @Mapping(target = "status", source = "status")
        }
    )
    OrderEntity toEntity(OrderTO order);
    
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "status", source = "order.status")
    OrderTO toDTO(OrderEntity order);
    
    List<OrderTO> toDTOList(List<OrderEntity> orders);
}

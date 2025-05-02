package fast_order.mapper;

import fast_order.commons.enums.OrderStatus;
import fast_order.dto.OrderTO;
import fast_order.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.UUID;

@Mapper(
    componentModel = "spring",
    uses = {UserMapper.class, ProductMapper.class, OrderStatus.class, UUID.class}
)
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

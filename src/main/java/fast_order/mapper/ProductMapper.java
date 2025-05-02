package fast_order.mapper;

import fast_order.dto.ProductTO;
import fast_order.entity.ProductEntity;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", uses = {UUID.class})
public interface ProductMapper {
    ProductEntity toEntity(ProductTO product);
    
    ProductTO toDTO(ProductEntity product);
    
    List<ProductTO> toDTOList(List<ProductEntity> products);
}

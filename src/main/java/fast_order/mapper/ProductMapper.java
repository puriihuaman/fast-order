package fast_order.mapper;

import fast_order.dto.ProductTO;
import fast_order.entity.ProductEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductEntity toEntity(ProductTO product);
    
    ProductTO toDTO(ProductEntity product);
    
    List<ProductTO> toDTOList(List<ProductEntity> products);
}

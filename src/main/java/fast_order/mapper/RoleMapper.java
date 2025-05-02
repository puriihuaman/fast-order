package fast_order.mapper;

import fast_order.dto.RoleTO;
import fast_order.entity.RoleEntity;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", uses = {UUID.class})
public interface RoleMapper {
    RoleEntity toEntity(RoleTO role);
    
    RoleTO toDTO(RoleEntity role);
    
    List<RoleTO> toDTOList(List<RoleEntity> roles);
}

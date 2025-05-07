package fast_order.mapper;

import fast_order.dto.UserTO;
import fast_order.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", uses = {UUID.class})
public interface UserMapper {
    
    @Mapping(target = "role.id", source = "roleId")
    UserEntity toEntity(UserTO user);
    
    @Mapping(target = "roleId", source = "role.id")
    UserTO toDTO(UserEntity user);
    
    List<UserTO> toDTOList(List<UserEntity> users);
}

package fast_order.mapper;

import fast_order.dto.NotificationTO;
import fast_order.entity.NotificationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    NotificationEntity toEntity(NotificationTO notification);
}

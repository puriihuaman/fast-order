package fast_order.mapper;

import fast_order.dto.NotificationTO;
import fast_order.entity.NotificationEntity;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = {UUID.class})
public interface NotificationMapper {
    NotificationEntity toEntity(NotificationTO notification);
}

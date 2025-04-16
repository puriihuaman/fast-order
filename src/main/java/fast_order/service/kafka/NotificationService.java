package fast_order.service.kafka;

import fast_order.dto.KafkaNotificationTO;
import fast_order.dto.NotificationTO;
import fast_order.entity.NotificationEntity;
import fast_order.mapper.NotificationMapper;
import fast_order.repository.NotificationRepository;
import fast_order.service.kafka.use_case.NotificationServiceUseCase;
import org.springframework.stereotype.Service;

@Service
public class NotificationService implements NotificationServiceUseCase {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    
    public NotificationService(
        NotificationRepository notificationRepository,
        NotificationMapper notificationMapper
    )
    {
        this.notificationRepository = notificationRepository;
        this.notificationMapper = notificationMapper;
    }
    
    @Override
    public void createNotification(KafkaNotificationTO notification) {
        NotificationTO
            newNotification =
            NotificationTO
                .builder()
                .message(notification.getMessage())
                .orderId(notification.getOrderId())
                .build();
        
        NotificationEntity notificationToSave = notificationMapper.toEntity(newNotification);
        NotificationEntity notificationSaved = notificationRepository.save(notificationToSave);
        
        System.out.println("------------------");
        System.out.println("------------------");
        System.out.println("NOTIFICATION SAVED");
        System.out.println(notificationSaved.getId());
        System.out.println(notificationSaved.getMessage());
        System.out.println(notificationSaved.getOrderId());
        System.out.println(notificationSaved.getCreatedAt());
        System.out.println("------------------");
        System.out.println("------------------");
    }
}

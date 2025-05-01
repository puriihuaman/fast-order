package fast_order.service.use_case;

import fast_order.dto.KafkaNotificationTO;

public interface NotificationServiceUseCase {
    void createNotification(KafkaNotificationTO notification);
}

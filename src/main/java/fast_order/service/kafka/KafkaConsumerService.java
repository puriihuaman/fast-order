package fast_order.service.kafka;

import fast_order.dto.KafkaNotificationTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    private final NotificationService notificationService;
    
    public KafkaConsumerService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    
    @KafkaListener(topics = "${KAFKA_TOPIC_NAME}", groupId = "${KAFKA_TOPIC_GROUP}")
    public void consumerNotification(KafkaNotificationTO notification) {
        notificationService.createNotification(notification);
    }
}

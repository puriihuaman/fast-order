package fast_order.service.kafka;

import fast_order.dto.KafkaNotificationTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    @Value("${KAFKA_TOPIC_NAME}")
    private String topicName;
    private final KafkaTemplate<String, KafkaNotificationTO> kafkaTemplate;
    
    public KafkaProducerService(KafkaTemplate<String, KafkaNotificationTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    
    public void sendNotification(KafkaNotificationTO notification) {
        kafkaTemplate.send(topicName, notification);
    }
}

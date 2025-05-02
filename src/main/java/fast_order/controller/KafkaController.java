package fast_order.controller;

import fast_order.dto.KafkaNotificationTO;
import fast_order.commons.enums.APISuccess;
import fast_order.service.kafka.KafkaProducerService;
import fast_order.utils.APIResponseData;
import fast_order.utils.APIResponseHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/topics")
public class KafkaController {
    private final KafkaProducerService producerService;
    
    public KafkaController(KafkaProducerService producerService) {
        this.producerService = producerService;
    }
    
    @GetMapping("send")
    public ResponseEntity<APIResponseData> sendMessage(@RequestParam String message) {
        KafkaNotificationTO
            notification =
            KafkaNotificationTO.builder().message(message).orderId(UUID.randomUUID()).build();
        producerService.sendNotification(notification);
        
        APISuccess.RESOURCE_RETRIEVED.setMessage("The topic was sent successfully.");
        
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_RETRIEVED, message);
    }
}

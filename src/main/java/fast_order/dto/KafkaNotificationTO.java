package fast_order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Data Transfer Object (DTO) for sending notifications via Kafka.
 * *
 * Contains the essential information needed to send a notification message related to an order
 * through a Kafka topic. This DTO is typically used for asynchronous communication between services.
 * *
 * Lombok annotations ({@code Builder}, {@code Getter}, {@code Setter}) automatically generate the
 * builder pattern, getters, and setters.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(
    name = "KafkaNotification",
    description = "DTO for sending order-related notifications via Kafka."
)
public class KafkaNotificationTO {
    @Schema(
        description = "The content of the notification message to be sent via Kafka.",
        example = "Order #456 has been successfully processed."
    )
    @JsonProperty(value = "message")
    private String message;
    
    @Schema(
        description = "ID of the order associated with this Kafka notification.",
        example = "98765432-10fe-dcba-9876-543210fedcba"
    )
    @JsonProperty(value = "orderId")
    private UUID orderId;
}

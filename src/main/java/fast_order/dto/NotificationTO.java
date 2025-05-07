package fast_order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object (DTO) representing a notification.
 * *
 * Contains information about a notification, including its message, the associated order ID,
 * and the creation timestamp.
 * It is used to transfer notification data, for example, in API responses.
 * *
 * Lombok annotations ({@code Builder}, {@code Getter}, {@code Setter}) automatically generate the
 * builder pattern, getters, and setters.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(name = "Notification", description = "DTO that represents a notification in the system.")
public class NotificationTO {
    @Schema(
        description = "Unique notification ID.", example = "a1b2c3d4-e5f6-7890-1234-567890abcdef",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    
    @Schema(
        description = "The content of the notification message.",
        example = "Your order #123 has been updated to 'SHIPPED'."
    )
    @JsonProperty(value = "message")
    private String message;
    
    @Schema(
        description = "ID of the order associated with this notification. Related to the 'Order' entity.",
        example = "f9e8d7c6-b5a4-3210-fedc-ba9876543210"
    )
    @JsonProperty(value = "orderId")
    private UUID orderId;
    
    @Schema(
        description = "Date and time the notification was created.",
        example = "2025-05-06T12:00:00", accessMode = Schema.AccessMode.READ_ONLY
    )
    @CreationTimestamp
    @JsonProperty(value = "createdAt", access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt = LocalDateTime.now();
}

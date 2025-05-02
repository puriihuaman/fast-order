package fast_order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificationTO {
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    
    @JsonProperty(value = "message")
    private String message;
    
    @JsonProperty(value = "orderId")
    private UUID orderId;
    
    @Builder.Default
    @CreationTimestamp
    @JsonProperty(value = "createdAt", access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt = LocalDateTime.now();
}

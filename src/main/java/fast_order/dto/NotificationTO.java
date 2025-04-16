package fast_order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificationTO {
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private Long id;
    
    @JsonProperty(value = "message")
    private String message;
    
    @JsonProperty(value = "orderId")
    private Long orderId;
    
    @CreationTimestamp
    @JsonProperty(value = "createdAt")
    private LocalDateTime createdAt = LocalDateTime.now();
}

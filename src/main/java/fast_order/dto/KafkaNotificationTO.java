package fast_order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class KafkaNotificationTO {
    @JsonProperty(value = "message")
    private String message;
    
    @JsonProperty(value = "orderId")
    private Long orderId;
}

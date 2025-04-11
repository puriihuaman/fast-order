package fast_order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import fast_order.enums.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Setter
public class OrderTO {
    @JsonProperty(value = "order_id", access = JsonProperty.Access.READ_ONLY)
    private Long id;
    
    @NotNull(message = "{field.null}")
    @Min(value = 0, message = "{order.amount.min}")
    @Positive(message = "{order.amount.positive}")
    @JsonProperty(value = "amount")
    private Integer amount;
    
    @NotNull(message = "{order.user.id}")
    @JsonProperty(value = "userId")
    private Long userId;
    
    @NotNull(message = "{order.product.id}")
    @JsonProperty(value = "productId")
    private Long productId;
    
    @Enumerated(EnumType.STRING)
    @JsonProperty(value = "status")
    private OrderStatus status = OrderStatus.PENDING;
    
    @CreationTimestamp
    @JsonProperty(value = "createdAt")
    private LocalDateTime createdAt;
}

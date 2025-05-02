package fast_order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import fast_order.commons.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
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
import java.util.UUID;

/**
 * Data Transfer Object (DTO) to represent order information.
 * -
 * Contains information about the order, including its status, relationships to other entities and
 * validation constraint.
 * It is used to transfer data between application layers (example: between the controller and the
 * service) or in API responses.
 * -
 * Lombok annotations ({@code Builder}, {@code Getter}, {@code Setter}) automatically generate the
 * builder pattern, getters and setters. Validation annotations ({@code NotNull}, {@code Min}, etc.)
 * are applied based on business rules.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(name = "Order", description = "DTO that represents an order registered in the system.")
public class OrderTO {
    @Schema(description = "Unique order ID.", example = "1")
    @JsonProperty(value = "orderId", access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    
    @Schema(
        description = "Quantity of the product in the order. Must be greater than zero.",
        example = "10", requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "{field.null}")
    @Min(value = 1, message = "{order.amount.min}")
    @Positive(message = "{order.amount.positive}")
    @Builder.Default
    @JsonProperty(value = "amount")
    private Integer amount = 1;
    
    @Schema(
        description = "ID of the user placen the order. Related to the 'User' entity.",
        example = "1"
    )
    @NotNull(message = "{order.user.id}")
    @JsonProperty(value = "userId")
    private UUID userId;
    
    @Schema(
        description = "Product ID associated with the order. Related to the 'Product' entity.",
        example = "1"
    )
    @NotNull(message = "{order.product.id}")
    @JsonProperty(value = "productId")
    private UUID productId;
    
    @Schema(
        description = "Current status of the order.",
        examples = {"PENDING", "FINISHED", "CANCELLED"}, defaultValue = "PENDING"
    )
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @JsonProperty(value = "status")
    private OrderStatus status = OrderStatus.PENDING;
    
    @Schema(
        description = "Date and time the order was created.", example = "2025-04-13T15:42:00"
    )
    @CreationTimestamp
    @JsonProperty(value = "createdAt")
    private LocalDateTime createdAt;
}

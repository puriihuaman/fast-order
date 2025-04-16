package fast_order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) o update the price of an existing product in the system.
 * -
 * Contains the information necessary to modify a product's price, ensuring basic integrity and
 * format validation.
 * It is typically used in partial update operations (PATCH).
 * -
 * Lombok annotations ({@code @Getter}, {@code @Setter}) automatically generate access and
 * modification methods. Validation annotations ensure compliance with business rules.
 */
@Getter
@Setter
@Schema(
    name = "PriceUpdate",
    description = "DTO used to updated the price of an existing product in the system."
)
public class PriceUpdateTO {
    @Schema(
        description = "New price assigned to the product. Must be greater than Zero.",
        example = "50.00", requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "{field.null}")
    @DecimalMin(value = "0.01", message = "{product.price.min}")
    @JsonProperty(value = "price")
    private Double price;
}

package fast_order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Data Transfer Object (DTO) to update the available stock of an existing product in the system.
 * -
 * This record contains the information needed to increase the available inventory
 * quantity of a specific product.
 * It is commonly used in partial update (PATCH) operations for inventory adjustments.
 * -
 * Lombok annotations ({@code @Getter}, {@code @Setter} in a record) automatically generate
 * field access methods.
 * Validation annotations ensure that the stock increment complies with the defined business rules.
 *
 * @param amount The amount to increase in the current stock of the product.
 *               Must be a value greater than 0.
 */
@Schema(
    name = "StockUpdate",
    description = "DTO used to increase the available stock of an existing product in the system."
)
public record StockUpdateTO (
    @Schema(
        description = "Amount to increase in the current stock of the product. Must be greater than 0.",
        example = "1", requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "{field.null}")
    @Min(value = 1, message = "{amount.min}")
    @Positive(message = "{amount.positive}")
    @JsonProperty(value = "amount")
    Integer amount
){}

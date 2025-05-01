package fast_order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object (DTO) o update the price of an existing product in the system.
 * -
 * This record contains the information needed to modify the price of a specific product
 * within the system.
 * It is commonly used in partial update operations (PATCH).
 * -
 * Lombok annotations ({@code @Getter}, {@code @Setter} on a record) automatically generate
 * the methods for accessing the fields.
 * Validation annotations ensure that the new price complies with the defined business rules.
 *
 * @param price The new price assigned to the product. Must be greater than zero.
 */
@Schema(
    name = "PriceUpdate",
    description = "DTO used to updated the price of an existing product in the system."
)
public record PriceUpdateTO (
    @Schema(
        description = "New price assigned to the product. Must be greater than Zero.",
        example = "50.00", requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "{field.null}")
    @DecimalMin(value = "0.01", message = "{product.price.min}")
    @JsonProperty(value = "price")
    Double price
){}

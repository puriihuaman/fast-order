package fast_order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) to update the available stock of an existing product in the system.
 * -
 * Contains the information necessary to increase the available quantity of a product, ensuring
 * basic integrity and format validation.
 * It is typically used in partial update operations (PATCH) for inventory adjustments.
 * -
 * Lombok annotations ({@code @Getter}, {@code @Setter}) automatically generate access and
 * modification methods.
 * Validation annotations ensure compliance with business rules for stock increments.
 */
@Getter
@Setter
@Schema(
    name = "StockUpdate",
    description = "DTO used to increase the available stock of an existing product in the system."
)
public class StockUpdateTO {
    @Schema(
        description = "Amount to increase in the current stock of the product. Must be greater than 0.",
        example = "1", requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "{field.null}")
    @Min(value = 1, message = "{amount.min}")
    @Positive(message = "{amount.positive}")
    @JsonProperty(value = "amount")
    private Integer amount;
}

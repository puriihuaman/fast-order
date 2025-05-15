package fast_order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Data Transfer Object (DTO) representing a product registered in the system.
 * -
 * Contains core product information including pricing, inventory details, and descriptions.
 * Used for product management operations and API data exchange.
 * -
 * Lombok annotations ({@code @Builder}, {@code @Getter}, {@code @Setter}) generate boilerplate
 * code for object construction and accessors.
 * Validation annotations enforce business rules and data integrity constraints.
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(
    name = "Product",
    description = """
                  A DTO that represents a product registered in the system.
                  It includes basic information, price, and available stock.
                  """
)
public class ProductTO {
    @Schema(
        description = "Unique product ID.", example = "1", accessMode = Schema.AccessMode.READ_ONLY
    )
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    
    @Schema(
        description = "Unique product name.", example = "Laptop Apple M1",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotEmpty(message = "{field.empty}")
    @Size(min = 4, max = 60, message = "{product.name.size}")
    @JsonProperty(value = "name")
    private String name;
    
    @Schema(
        description = "Quantity available in product inventory.", example = "5",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "{field.null}")
    @PositiveOrZero(message = "{product.stock.positive}")
    @Min(value = 0, message = "{product.stock.min}")
    @JsonProperty(value = "stock")
    private Integer stock;
    
    @Schema(
        description = "Unit price of the product.", example = "500.00",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "{field.null}")
    @Positive(message = "{product.price.positive}")
    @DecimalMin(value = "0.01", message = "{product.price.min}")
    @JsonProperty(value = "price")
    private Double price;
    
    @Schema(
        description = "Detailed product description.",
        example = "Laptop Apple M1 color negro, 16GB RAM, 500GB SSD.",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotEmpty(message = "{field.empty}")
    @Size(min = 10, max = 200, message = "{product.description.size}")
    @JsonProperty(value = "description")
    private String description;
}

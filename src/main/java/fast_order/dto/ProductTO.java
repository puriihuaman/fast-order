package fast_order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductTO {
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private Long id;
    
    @NotNull(message = "{field.null}")
    @NotEmpty(message = "{field.empty}")
    @Size(min = 4, max = 60, message = "{product.name.size}")
    @JsonProperty(value = "name")
    private String name;
    
    @NotNull(message = "{field.null}")
    @PositiveOrZero(message = "{product.stock.positive}")
    @Min(value = 0, message = "{product.stock.min}")
    @JsonProperty(value = "stock")
    private Integer stock = 0;
    
    @NotNull(message = "{field.null}")
    @Positive(message = "{product.price.positive}")
    @DecimalMin(value = "0.01", message = "{product.price.min}")
    @JsonProperty(value = "price")
    private Double price;
    
    @NotNull(message = "{field.null}")
    @Size(min = 10, max = 200, message = "{product.description.size}")
    @JsonProperty(value = "description")
    private String description;
}

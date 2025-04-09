package fast_order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriceUpdateTO {
    @NotNull(message = "{field.null}")
    @DecimalMin(value = "0.01", message = "El precio debe ser positivo mayor que 0.")
    @JsonProperty(value = "price")
    private Double price;
}

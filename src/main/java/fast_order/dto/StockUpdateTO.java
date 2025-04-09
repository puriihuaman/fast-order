package fast_order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockUpdateTO {
    @NotNull(message = "{field.null}")
    @Min(value = 1, message = "El valor mínimo permitido es 1.")
    @JsonProperty(value = "amount")
    private Integer amount;
}

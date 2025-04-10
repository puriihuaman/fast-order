package fast_order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockUpdateTO {
    @NotNull(message = "{field.null}")
    @Min(value = 1, message = "{amount.min}")
    @Positive(message = "{amount.positive}")
    @JsonProperty(value = "amount")
    private Integer amount;
}

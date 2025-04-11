package fast_order.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum OrderStatus {
    PENDING("pending"), FINISHED("finished"), CANCELLED("cancelled");
    
    private final String value;
    
    OrderStatus(String value) {
        this.value = value;
    }
    
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static OrderStatus fromValue(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Status cannot be null. Valid status: " +
                                                   getValidOrderStatus());
        }
        
        for (OrderStatus status : OrderStatus.values()) {
            if (status.getValue().equalsIgnoreCase(value)) return status;
        }
        throw new IllegalArgumentException("Invalid order status: " + value);
    }
    
    @JsonValue
    public String getValue() {
        return value;
    }
    
    /**
     * Retorna una cadena de texto, con los valores de los estados.
     *
     * @return "1, 2, 0"
     */
    private static String getValidOrderStatus() {
        return Arrays.stream(values()).map(OrderStatus::getValue).collect(Collectors.joining(", "));
    }
}

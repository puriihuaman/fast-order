package fast_order.commons.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Represents the possible states of an order in the system.
 * -
 * Used to track order lifecycle and enforce business rules during state transitions.
 * The enum values are serialized/deserialized using lowercase strings in JSON format.
 */
public enum OrderStatus {
    /**
     * Initial state of a newly created order. The order is awaiting processing.
     */
    PENDING("pending"),
    /**
     * Final state indicating successful order completion and fulfillment.
     */
    FINISHED("finished"),
    /**
     * Terminal state for orders that were canceled before completion.
     */
    CANCELLED("cancelled");
    
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
     * Returns a text string, with the values of the states.
     *
     * @return "pending, finished, cancelled"
     */
    private static String getValidOrderStatus() {
        return Arrays.stream(values()).map(OrderStatus::getValue).collect(Collectors.joining(", "));
    }
}

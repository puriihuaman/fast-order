package fast_order.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import fast_order.exception.APIRequestException;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Enumeration representing user roles in the authentication and authorization system.
 * -
 * Defines access levels and permissions within the application.
 * The string values are used for JSON serialization/deserialization in lowercase format.
 */
public enum RoleType {
    /**
     * Administrative role with full system access and management privileges.
     */
    ADMIN("admin"),
    /**
     * Standard user role with basic application functionality access.
     */
    USER("user"),
    /**
     * Temporary guest role with limited access rights.
     */
    INVITED("invited");
    
    private final String value;
    
    RoleType(String value) {
        this.value = value;
    }
    
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static RoleType fromString(String value) {
        if (value == null || value.isBlank()) {
            APIError.BAD_REQUEST.setTitle("Role cannot be null or empty");
            APIError.BAD_REQUEST.setMessage("Role cannot be null or empty. Valid roles: " +
                                                getValidRoleNames());
            throw new APIRequestException(APIError.BAD_REQUEST);
        }
        
        for (RoleType role : RoleType.values()) {
            if (role.value.equalsIgnoreCase(value)) return role;
        }
        APIError.BAD_REQUEST.setTitle("Invalid Role");
        APIError.BAD_REQUEST.setMessage("Invalid Role: " + value);
        
        throw new APIRequestException(APIError.BAD_REQUEST);
    }
    
    @JsonValue
    public String getRoleName() {
        return value;
    }
    
    private static String getValidRoleNames() {
        return Arrays.stream(values()).map(RoleType::getRoleName).collect(Collectors.joining(", "));
    }
}

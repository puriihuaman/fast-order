package fast_order.utils;

import fast_order.commons.enums.APISuccess;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Standardized response format for successful API operations.
 * *
 * Wraps the operation result with consistent metadata including status information
 * and timestamps. Used across all API endpoints to maintain uniform response structures.
 * *
 * Lombok {@code @Getter} annotation generates getter methods for all fields.
 */
@Getter
@Schema(
    name = "APIResponseData",
    description = """
                  DTO representing a standard successful API response.
                  It contains the operation result, status, and response metadata.
                  """,
    example = """
              {
                "data": {
                  "name": "Jorge Suarez",
                  "email": "jorge@gmail.com",
                  "password": "XSS.XSS.XSS....XSS",
                  "signUpDate": "2025-04-12",
                  "totalSpent": 150.5,
                  "roleId": 2
                },
                "hasError": false,
                "message": "Resource successfully recovered.",
                "statusCode": 200,
                "timestamp": "2025-04-13T15:42:00"
              }
              """
)
public class APIResponseData {
    @Schema(
        description = "Object with the requested information or result of the operation.",
        example = """
                  {
                    "name": "Jorge Suarez",
                    "email": "jorge@gmail.com",
                    "password": "XSS.XSS.XSS....XSS",
                    "signUpDate": "2025-04-12",
                    "totalSpent": 150.50,
                    "roleId": 2
                  }
                  """
    )
    private final Object data;
    
    @Schema(
        description = "Indicates whether the operation failed. Always false for successful responses.",
        example = "false"
    )
    private final Boolean hasError = false;
    
    @Schema(
        description = "Message briefly describing the result of the operation.",
        example = "Resource successfully recovered."
    )
    private final String message;
    
    @Schema(description = "HTTP code associated with the operation.", example = "200")
    private final Integer statusCode;
    
    @Schema(
        description = "Date and time the response is generated.",example = "2025-04-13T15:42:00"
    )
    private final LocalDateTime timestamp = LocalDateTime.now();
    
    /**
     * Constructor that initializes the standard API success response.
     *
     * @param success APISuccess instance containing the HTTP message and status.
     * @param data    Object with the data resulting from the operation.
     */
    public APIResponseData(APISuccess success, Object data) {
        this.data = data;
        this.message = success.getMessage();
        this.statusCode = success.getStatus().value();
    }
}

package fast_order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Map;

/**
 * Data Transfer Object (DTO) that represents a standardized error response for API operations.
 * -
 * Contains structured information about errors, including metadata and validation details
 * Used to provide consistent error formatting across all API endpoints.
 * -
 * Lombok annotations ({@code @Getter}, {@code @Setter}) generate accessors mutators automáticamente.
 */
@Getter
@Setter
@Schema(
    name = "ErrorResponse", description = "DTO representing a standard error response for the API."
)
public class ErrorResponseTO {
    @Schema(description = "Indicates whether the response contains an error.", example = "true")
    private Boolean hasError = true;
    
    @Schema(description = "Short title of the error.", example = "Invalid data")
    private String title;
    
    @Schema(
        description = "Detailed error message.",
        example = "The requested data contains invalid values or an incorrect format."
    )
    private String message;
    
    @Schema(description = "HTTP code associated with the error.", example = "400")
    private Integer statusCode;
    
    @Schema(
        description = "Specific reasons for the error. Useful for validation errors.",
        example = """
                  {
                    "field_name": "The name is required.",
                    "another_field": "Invalid format."
                  }
                  """
    )
    private Map<String, String> reasons;
    
    @Schema(
        description = "Date and time the error occurred.", example = "2025-04-13T15:42:00"
    )
    private LocalDateTime timestamp = ZonedDateTime.now().toLocalDateTime();
    
    public ErrorResponseTO(
        String title,
        String message,
        Integer statusCode,
        Map<String, String> reasons
    )
    {
        super();
        this.title = title;
        this.message = message;
        this.statusCode = statusCode;
        this.reasons = reasons;
    }
}
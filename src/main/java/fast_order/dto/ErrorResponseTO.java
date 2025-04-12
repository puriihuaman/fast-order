package fast_order.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Map;

@Getter
@Setter
public class ErrorResponseTO {
    private Boolean hasError = true;
    private String title;
    private String message;
    private Integer statusCode;
    private Map<String, String> reasons;
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